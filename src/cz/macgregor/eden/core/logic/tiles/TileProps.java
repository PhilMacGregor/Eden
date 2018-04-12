package cz.macgregor.eden.core.logic.tiles;

/**
 * first type of properties describing a field.
 * 
 * @todo the field properties system is going to be completely overhauled and
 *       this class will no longer be supported in future
 * @author MacGregor
 *
 */
public class TileProps {
	private boolean water;
	private boolean canBuild;
	private boolean buildings;
	private int fertility;
	private int wood;
	private int stone;
	private int ore;
	private int danger;

	/**
	 * constructor.
	 */
	public TileProps() {
		// create empty instance
	}

	/**
	 * constructor from other tile props.
	 * 
	 * @param other
	 *            other props
	 */
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

	/**
	 * sets water, returns this.
	 * 
	 * @param water
	 *            water
	 * @return this
	 */
	public TileProps withWater(boolean water) {
		this.water = water;
		return this;
	}

	/**
	 * sets buildings, returns this.
	 * 
	 * @param buildings
	 *            buildings
	 * @return this
	 */
	public TileProps withBuildings(boolean buildings) {
		this.buildings = buildings;
		return this;
	}

	/**
	 * sets canBuild, returns this.
	 * 
	 * @param canBuild
	 *            canBuild
	 * @return this
	 */
	public TileProps withCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
		return this;
	}

	/**
	 * sets fertility, returns this.
	 * 
	 * @param fertility
	 *            fertility
	 * @return this
	 */
	public TileProps withFertility(int fertility) {
		this.fertility = fertility;
		return this;
	}

	/**
	 * sets wood, returns this.
	 * 
	 * @param wood
	 *            wood
	 * @return this
	 */
	public TileProps withWood(int wood) {
		this.wood = wood;
		return this;
	}

	/**
	 * sets stone, returns this.
	 * 
	 * @param stone
	 *            stone
	 * @return this
	 */
	public TileProps withStone(int stone) {
		this.stone = stone;
		return this;
	}

	/**
	 * sets ore, returns this.
	 * 
	 * @param ore
	 *            ore
	 * @return this
	 */
	public TileProps withOre(int ore) {
		this.ore = ore;
		return this;
	}

	/**
	 * sets danger, returns this.
	 * 
	 * @param danger
	 *            danger
	 * @return this
	 */
	public TileProps withDanger(int danger) {
		this.danger = danger;
		return this;
	}

	/**
	 * getter.
	 * 
	 * @return the water
	 */
	public boolean isWater() {
		return this.water;
	}

	/**
	 * setter.
	 * 
	 * @param water
	 *            the water to set
	 */
	public void setWater(boolean water) {
		this.water = water;
	}

	/**
	 * getter.
	 * 
	 * @return the canBuild
	 */
	public boolean isCanBuild() {
		return this.canBuild;
	}

	/**
	 * setter.
	 * 
	 * @param canBuild
	 *            the canBuild to set
	 */
	public void setCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
	}

	/**
	 * getter.
	 * 
	 * @return the fertility
	 */
	public int getFertility() {
		return this.fertility;
	}

	/**
	 * setter.
	 * 
	 * @param fertility
	 *            the fertility to set
	 */
	public void setFertility(int fertility) {
		this.fertility = fertility;
	}

	/**
	 * getter.
	 * 
	 * @return the wood
	 */
	public int getWood() {
		return this.wood;
	}

	/**
	 * setter.
	 * 
	 * @param wood
	 *            the wood to set
	 */
	public void setWood(int wood) {
		this.wood = wood;
	}

	/**
	 * getter.
	 * 
	 * @return the stone
	 */
	public int getStone() {
		return this.stone;
	}

	/**
	 * setter.
	 * 
	 * @param stone
	 *            the stone to set
	 */
	public void setStone(int stone) {
		this.stone = stone;
	}

	/**
	 * getter.
	 * 
	 * @return the ore
	 */
	public int getOre() {
		return this.ore;
	}

	/**
	 * setter.
	 * 
	 * @param ore
	 *            the ore to set
	 */
	public void setOre(int ore) {
		this.ore = ore;
	}

	/**
	 * getter.
	 * 
	 * @return the danger
	 */
	public int getDanger() {
		return this.danger;
	}

	/**
	 * setter.
	 * 
	 * @param danger
	 *            the danger to set
	 */
	public void setDanger(int danger) {
		this.danger = danger;
	}

	/**
	 * getter.
	 * 
	 * @return the buildings
	 */
	public boolean hasBuildings() {
		return buildings;
	}

	/**
	 * setter.
	 * 
	 * @param buildings
	 *            the buildings to set
	 */
	public void setBuildings(boolean buildings) {
		this.buildings = buildings;
	}

}
