package cz.macgregor.eden.core.logic.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.util.filecrawler.FileCrawler;
import cz.macgregor.eden.util.filecrawler.ResourceEntry;

public class ActionHolder {
	private Map<String, ActionEntry> entries;

	private Map<TriggerType, List<ActionEntry>> entriesByTrigger;

	public static ActionHolder getInstance() {
		return InstanceHolder.INSTANCE.instance;
	}

	private ActionHolder() {
		this.entries = new HashMap<>();
		this.entriesByTrigger = new HashMap<>();

		try {
			Collection<ResourceEntry<?>> resources = FileCrawler.getInstance()
					.searchFolder("cz.macgregor.eden.core.logic.actions.impl", ".class");
			for (ResourceEntry<?> res : resources) {
				Class<?> clazz = (Class<?>) res.getResource();
				ActionInfo actionInfo = clazz.getAnnotation(ActionInfo.class);

				ActionEntry entry = new ActionEntry(actionInfo.trigger(), (Action) clazz.newInstance());
				entries.put(actionInfo.name(), entry);
				getActionsByTrigger(actionInfo.trigger()).add(entry);
			}
			
		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException("failed to instantiate the action.", e);
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

	public static void activateTrigger(TriggerType trig) {
		for (ActionEntry holder : actionsByTrigger(trig)) {

			for (Entity ent : holder.subscribers) {
				holder.action.doAction(ent);
			}

		}
	}

	public static void activateTrigger(TriggerType trig, Entity... ents) {
		for (ActionEntry holder : actionsByTrigger(trig)) {

			for (Entity ent : ents) {
				if (ent.getActions().contains(holder.action)) {
					holder.action.doAction(ent);
				}

			}

		}
	}

	public class ActionEntry {
		private final Action action;
		private final TriggerType trigger;
		private final List<Entity> subscribers;

		private ActionEntry(TriggerType trigger, Action action) {
			this.trigger = trigger;
			this.action = action;
			this.subscribers = new ArrayList<>();
		}

		public void addSubscriber(Entity ent) {
			subscribers.add(ent);
		}

		public void removeSubscriber(Entity ent) {
			subscribers.remove(ent);
		}

		/**
		 * @return the action
		 */
		public Action getAction() {
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
