package cz.macgregor.eden.core.logic.actions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.Subscriber;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.util.filecrawler.FileCrawler;
import cz.macgregor.eden.util.filecrawler.ResourceEntry;

public class ActionHolder {
	private final Map<String, ActionEntry> entries;

	private final Map<TriggerType, List<ActionEntry>> entriesByTrigger;

	private final Map<Identifier<? extends HasAction>, ActionEntry[]> defaultActions;

	public static ActionHolder getInstance() {
		return InstanceHolder.INSTANCE.instance;
	}

	private ActionHolder() {
		this.entries = new HashMap<>();
		this.entriesByTrigger = new HashMap<>();
		this.defaultActions = new HashMap<>();

		initEntriesByTrigger();
		initDefaultActions(TileType.class);
		initDefaultActions(EntityType.class);

	}

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
					defaultActions.put(identifier, foundActionEntries.toArray(new ActionEntry[foundActionEntries.size()]));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException("failed to initialize EntityFactory.", e);
				}
			}
		}
	}

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

	public List<ActionEntry> getActionsByTrigger(TriggerType trig) {
		Map<TriggerType, List<ActionEntry>> entryMap = this.entriesByTrigger;
		List<ActionEntry> entriesByTrigger = entryMap.get(trig);

		if (entriesByTrigger == null) {
			entriesByTrigger = new ArrayList<>();
			entryMap.put(trig, entriesByTrigger);
		}

		return entriesByTrigger;
	}

	public static List<ActionEntry> actionsByTrigger(TriggerType trig) {
		return getInstance().getActionsByTrigger(trig);
	}

	public static synchronized void activateTrigger(TriggerType trig) {
		for (ActionEntry holder : actionsByTrigger(trig)) {

			for (HasAction ent : holder.subscribers) {
				holder.action.doAction(ent);
			}

		}
	}

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

	public class ActionEntry {
		private final Action<HasAction>	action;
		private final TriggerType				trigger;
		private final List<HasAction>		subscribers;

		private ActionEntry(TriggerType trigger, Action<HasAction> action) {
			this.trigger = trigger;
			this.action = action;
			this.subscribers = new ArrayList<>();
		}

		public void addSubscriber(HasAction ent) {
			subscribers.add(ent);
		}

		public void removeSubscriber(Entity ent) {
			subscribers.remove(ent);
		}

		/**
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

	private enum InstanceHolder {
		INSTANCE;

		private ActionHolder instance;

		private InstanceHolder() {
			this.instance = new ActionHolder();
		}
	}

}
