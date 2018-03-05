package cz.macgregor.eden.core.logic.tiles;

public class TileProps {
	private boolean	water;
	private boolean	canBuild;
	private boolean	buildings;
	private int			fertility;
	private int			wood;
	private int			stone;
	private int			ore;
	private int			danger;

	public TileProps() {
		// create empty instance
	}

	public TileProps(TileProps other) {
		this.water = other.water;
		this.buildings = other.buildings;
		this.canBuild = other.canBuild;
		this.fertility = other.fertility;
		this.wood = other.wood;
		this.stone = other.stone;
		this.ore = other.ore;
		this.danger = other.danger;
	}
	
	public TileProps withWater(boolean water) {
		this.water = water;
		return this;
	}
	
	public TileProps withBuildings(boolean buildings) {
		this.buildings = buildings;
		return this;
	}
	
	public TileProps withCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
		return this;
	}
	
	public TileProps withFertility(int fertility) {
		this.fertility = fertility;
		return this;
	}
	
	public TileProps withWood(int wood) {
		this.wood = wood;
		return this;
	}
	
	public TileProps withStone(int stone) {
		this.stone = stone;
		return this;
	}
	
	public TileProps withOre(int ore) {
		this.ore = ore;
		return this;
	}
	
	public TileProps withDanger(int danger) {
		this.danger = danger;
		return this;
	}

	/**
	 * @return the water
	 */
	public boolean isWater() {
		return this.water;
	}

	/**
	 * @param water
	 *          the water to set
	 */
	public void setWater(boolean water) {
		this.water = water;
	}

	/**
	 * @return the canBuild
	 */
	public boolean isCanBuild() {
		return this.canBuild;
	}

	/**
	 * @param canBuild
	 *          the canBuild to set
	 */
	public void setCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
	}

	/**
	 * @return the fertility
	 */
	public int getFertility() {
		return this.fertility;
	}

	/**
	 * @param fertility
	 *          the fertility to set
	 */
	public void setFertility(int fertility) {
		this.fertility = fertility;
	}

	/**
	 * @return the wood
	 */
	public int getWood() {
		return this.wood;
	}

	/**
	 * @param wood
	 *          the wood to set
	 */
	public void setWood(int wood) {
		this.wood = wood;
	}

	/**
	 * @return the stone
	 */
	public int getStone() {
		return this.stone;
	}

	/**
	 * @param stone
	 *          the stone to set
	 */
	public void setStone(int stone) {
		this.stone = stone;
	}

	/**
	 * @return the ore
	 */
	public int getOre() {
		return this.ore;
	}

	/**
	 * @param ore
	 *          the ore to set
	 */
	public void setOre(int ore) {
		this.ore = ore;
	}

	/**
	 * @return the danger
	 */
	public int getDanger() {
		return this.danger;
	}

	/**
	 * @param danger
	 *          the danger to set
	 */
	public void setDanger(int danger) {
		this.danger = danger;
	}

	/**
	 * @return the buildings
	 */
	public boolean hasBuildings() {
		return buildings;
	}

	/**
	 * @param buildings
	 *          the buildings to set
	 */
	public void setBuildings(boolean buildings) {
		this.buildings = buildings;
	}

}
