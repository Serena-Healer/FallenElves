package scellena.fallen_elves.entities;

public interface ICustomEntity {

    double getEffectiveHealth();
    double getAttackDamage();
    default double getAntiKB(){ return 0; }

}
