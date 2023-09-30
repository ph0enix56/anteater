package cz.cvut.fit.anteater.entity;

import cz.cvut.fit.anteater.enumeration.Ability;

public class AbilityBonus {
	public Ability ability;
	public Integer bonus;

	public AbilityBonus() {}

	public AbilityBonus(Ability ability, Integer bonus) {
		this.ability = ability;
		this.bonus = bonus;
	}

	public Ability getAbility() {
		return ability;
	}

	public void setAbility(Ability ability) {
		this.ability = ability;
	}

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}

	@Override
	public String toString() {
		return "AbilityBonus [ability=" + ability + ", bonus=" + bonus + "]";
	}
}
