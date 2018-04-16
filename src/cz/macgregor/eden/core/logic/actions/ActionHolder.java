package cz.macgregor.eden.core.logic.actions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.Subscriber;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.util.filecrawler.FileCrawler;
import cz.macgregor.eden.util.filecrawler.ResourceEntry;

/**
 * this class holds a list of all action used by fields and entites by trigger
 * type. Can trigger specific actions both for one HasAction or for all of them
 * subscribed to the action.
 * 
 * @author MacGregor
 *
 */
public class ActionHolder {
	/** map of the action entries by name. */
	private final Map<String, ActionEntry> entries;
	/** map of the actions for each trigger. */
	private final Map<TriggerType, List<ActionEntry>> entriesByTrigger;
	/** default actions for each HasAction. */
	private final Map<Identifier<? extends HasAction>, ActionEntry[]> defaultActions;

	public static ActionHolder getInstance() {
		return InstanceHolder.INSTANCE.instance;
	}

	/**
	 * constructor.
	 */
	private ActionHolder() {
		this.entries = new HashMap<>();
		this.entriesByTrigger = new HashMap<>();
		this.defaultActions = new HashMap<>();

		initEntriesByTrigger();
		initDefaultActions(TileType.class);
		initDefaultActions(EntityType.class);

	}

	/**
	 * initialisation method to map the all actions and create the action
	 * entries based on their trigger. <br>
	 * <br>
	 * <ol>
	 * <li>Crawls the package where the actions to be mapped are expected using
	 * the FileCrawler.</li>
	 * <li>For each class annotated with ActionInfo, create a new ActionEntry,
	 * containing the trigger from the annotation and a new instance of the
	 * class.</li>
	 * <li>Also, put the action in the Entries map with a name from the
	 * annotation.</li>
	 * </ol>
	 * The ActionInfo annotation must be only used with the Action class. The
	 * type of the mapped class is currently unchecked and the illegal type will
	 * result in failure of the application on startup.
	 * 
	 */
	private void initEntriesByTrigger() {
		try {
			Collection<ResourceEntry<?>> resources = FileCrawler.getInstance()
					.searchFolder("cz.macgregor.eden.core.logic.actions", ".class");
			for (ResourceEntry<?> res : resources) {
				Class<?> clazz = (Class<?>) res.getResource();
				ActionInfo actionInfo = clazz.getAnnotation(ActionInfo.class);

				if (actionInfo == null) {
					continue;
				}

				@SuppressWarnings("unchecked")
				ActionEntry entry = new ActionEntry(actionInfo.trigger(), (Action<HasAction>) clazz.newInstance());
				entries.put(actionInfo.name(), entry);
				getActionsByTrigger(actionInfo.trigger()).add(entry);
			}

		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException("failed to instantiate the action.", e);
		}
	}

	/**
	 * Method that check a given enum (probably works with standard classes too,
	 * but this is not checked) and maps its instances to the map with their
	 * default actions. The class must implement an Identifier interface.<br>
	 * <br>
	 * <ol>
	 * <li>for each field of the class, check if it's annotated with
	 * Subscriber.</li>
	 * <li>for all actions from the Subscriber annotation, put an item to the
	 * defaultActions with the field as the key and the actions array as the
	 * value.</li>
	 * </ol>
	 * 
	 * @param clazz
	 *            class to be mapped
	 */
	private void initDefaultActions(Class<?> clazz) {
		for (Field fld : clazz.getFields()) {
			Subscriber subscriberInfo = fld.getAnnotation(Subscriber.class);

			if (Arrays.asList(fld.getType().getInterfaces()).contains(Identifier.class) && subscriberInfo != null) {

				String[] actions = subscriberInfo.value();
				List<ActionEntry> foundActionEntries = new ArrayList<>();
				for (String action : actions) {
					foundActionEntries.add(this.entries.get(action));
				}

				Identifier<?> identifier;
				try {
					identifier = (Identifier<?>) fld.get(null);
					defaultActions.put(identifier,
							foundActionEntries.toArray(new ActionEntry[foundActionEntries.size()]));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException("failed to initialize EntityFactory.", e);
				}
			}
		}
	}

	/**
	 * find an action entry by given name.
	 * 
	 * @param name
	 *            action name
	 * @return action entry
	 */
	public static ActionEntry byName(String name) {
		return getInstance().entries.get(name);
	}

	/**
	 * remove all subscribers from all actions.
	 */
	public static void reset() {
		for (ActionEntry entry : getInstance().entries.values()) {
			entry.subscribers.clear();
		}

	}

	/**
	 * convenience method, just actionsByTrigger called on getInstance().
	 * 
	 * @param trig
	 *            trigger
	 * @return actions for that trigger
	 */
	public List<ActionEntry> getActionsByTrigger(TriggerType trig) {
		Map<TriggerType, List<ActionEntry>> entryMap = this.entriesByTrigger;
		List<ActionEntry> entriesByTrigger = entryMap.get(trig);

		if (entriesByTrigger == null) {
			entriesByTrigger = new ArrayList<>();
			entryMap.put(trig, entriesByTrigger);
		}

		return entriesByTrigger;
	}

	/**
	 * get a list of all actions for a trigger type.
	 * 
	 * @param trig
	 *            trigger
	 * @return list of all actions for that trigger
	 */
	public static List<ActionEntry> actionsByTrigger(TriggerType trig) {
		return getInstance().getActionsByTrigger(trig);
	}

	/**
	 * execute every action for a given trigger on all subscribed HasAction
	 * items.
	 * 
	 * @param trig
	 *            trigger
	 */
	public static synchronized void activateTrigger(TriggerType trig) {
		for (ActionEntry holder : actionsByTrigger(trig)) {

			for (HasAction ent : holder.subscribers) {
				holder.action.doAction(ent);
			}

		}
	}

	/**
	 * execute every action for a given trigger only on given HasAction items.
	 * If the items do not have any action, ignore them.
	 * 
	 * @param trig
	 *            trigger
	 * @param ents
	 *            items to trigger
	 */
	public static void activateTrigger(TriggerType trig, HasAction... ents) {
		for (ActionEntry holder : actionsByTrigger(trig)) {

			for (HasAction ent : ents) {
				if (ent.getActions().contains(holder.action)) {
					holder.action.doAction(ent);
				}
			}
		}

	}

	public static ActionEntry[] getDefaultActions(Identifier<?> identifier) {
		return getInstance().defaultActions.get(identifier);
	}

	/**
	 * class for a list item holding a single action, a trigger and a list of
	 * subscriber items.
	 * 
	 * @author MacGregor
	 *
	 */
	public class ActionEntry {
		/** action held. */
		private final Action<HasAction> action;
		/** trigger. */
		private final TriggerType trigger;
		/** subscribed HasAction items. */
		private final List<HasAction> subscribers;

		/**
		 * constructor.
		 * 
		 * @param trigger
		 *            trigger
		 * @param action
		 *            action
		 */
		private ActionEntry(TriggerType trigger, Action<HasAction> action) {
			this.trigger = trigger;
			this.action = action;
			this.subscribers = new ArrayList<>();
		}

		/**
		 * add a subscriber.
		 * 
		 * @param ent
		 *            HasAction item
		 */
		public void addSubscriber(HasAction ent) {
			subscribers.add(ent);
		}

		/**
		 * remove a subscriber.
		 * 
		 * @param ent
		 *            HasAction item
		 */
		public void removeSubscriber(HasAction ent) {
			subscribers.remove(ent);
		}

		/**
		 * getter.
		 * 
		 * @return the action
		 */
		public Action<HasAction> getAction() {
			return action;
		}

		@Override
		public String toString() {
			return action.getClass().getSimpleName() + " - trigger: " + trigger;
		}

	}

	/**
	 * instance holder for the ActonHolder.
	 * 
	 * @author MacGregor
	 *
	 */
	private enum InstanceHolder {
		/** the ActionHolder instance. */
		INSTANCE;

		/** the instance. */
		private ActionHolder instance;

		/**
		 * constructor.
		 */
		private InstanceHolder() {
			this.instance = new ActionHolder();
		}
	}

}
