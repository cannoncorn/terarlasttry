package org.egordorichev.lasttry.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import org.egordorichev.lasttry.LastTry;
import org.egordorichev.lasttry.entity.*;
import org.egordorichev.lasttry.entity.components.PhysicsComponent;
import org.egordorichev.lasttry.entity.drop.Drop;
import org.egordorichev.lasttry.entity.drop.DroppedItem;
import org.egordorichev.lasttry.entity.player.Player;
<<<<<<< HEAD
import org.egordorichev.lasttry.graphics.Animation;
import org.egordorichev.lasttry.item.block.Block;
import org.egordorichev.lasttry.world.biome.Biome;

=======
>>>>>>> component-future
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public abstract class Enemy extends Entity {
    /**
     * Defined enemies
     */
    public static HashMap<Short, Class<? extends Enemy>> ENEMY_CACHE = new HashMap<>();

    //TODO To be removed and reimplemented
    /**
     * Static list created to allow looping through of enemies
     */
    public static ArrayList<Enemy> availEnemies = new ArrayList<>();

    /**
     * Max Ai for this enemy
     */
    protected static int maxAi;

    static {
        define(EnemyID.greenSlime, GreenSlime.class);
        define(EnemyID.blueSlime, BlueSlime.class);
        define(EnemyID.eyeOfCthulhu, EyeOfCthulhu.class);
        define(EnemyID.zombie, Zombie.class);

    }

    /**
     * Current Ai counter
     */
    protected int currentAi;
    /**
     * Enemy id
     */
=======
public abstract class Enemy extends CreatureWithAI {
>>>>>>> component-future
    protected int id;
    protected Texture texture;
    protected List<Drop> drops = new ArrayList<>();

<<<<<<< HEAD
    /**
     * Each biome has a maxinum number of enemies limit.  When deciding what enemy to spawn next in a biome, the
     * spawn weight of multiple enemies are added till the maximum number of enemies in the biome is complete.
     */
    private int spawnWeight;

    /**
     * Enemy name
     */
    protected String name;

    //TODO Should these parameters be replaced with an enum that encapsulates the stats?
    public Enemy(String name, short id, int maxHp, int defense, int damage, int spawnWeight) {
        super(maxHp, damage, defense);
        this.spawnWeight = spawnWeight;
        this.animations = new Animation[State.values().length];
        this.id = id;
        this.name = name;
    }

    public Enemy(short id, String name) {
        //TODO Should parameters be converted into an enum?
        //TODO Handle the 'spawnWeight' for a 'Boss' level enemy
        this(name, id, 10, 0, 5, 1);
        this.animations = new Animation[State.values().length];
        this.id = id;
    }

    public static void define(short id, Class<? extends Enemy> enemy) {
        // TODO: handle duplicates
        LastTry.debug("Defined [" + id + "] as " + enemy.getSimpleName());
        ENEMY_CACHE.put(id, enemy);

        Enemy enemyInstance = create(id);
        availEnemies.add(enemyInstance);
    }

    public static Enemy create(short id) {
        try {
            Class<? extends Enemy> aClass = ENEMY_CACHE.get(id);

            if (aClass != null) {
                return aClass.newInstance();
            } else {
                LastTry.log.warn("Enemy with id " + id + " is not found");
                return null;
            }
        } catch (Exception exception) {
            LastTry.handleException(exception);
            return null;
        }
    }

    @Override
    public void render() {
        this.animations[this.state.getId()].render(this.renderBounds.x, LastTry.world.getHeight() * Block.TEX_SIZE
                        - this.renderBounds.y - this.renderBounds.height, this.renderBounds.width, this.renderBounds.height,
                (this.direction == Direction.RIGHT), false);
    }

    @Override
    public void update(int dt) {
        super.update(dt);

        this.animations[this.state.getId()].update();

        if (LastTry.player.getHitbox().intersects(this.getHitbox())) {
            this.onPlayerCollision(LastTry.player);
        }

        // this.animations[this.state.getId()].update(dt);
        this.updateAI();
=======
    public Enemy(short id, int maxHp, int defense, int damage) {
        super(new PhysicsComponent(), new EnemyGraphicsComponent());

        this.stats.set(maxHp, 0, damage, defense);
	    this.id = id;
>>>>>>> component-future
    }

    public void updateAI() {

    }

    /**
     * Returns a boolean indicating whether the enemy can spawn based on the player's game conditions.
     * @return boolean
     */
    public boolean canSpawn(){
        return false;
    }


    @Override
    public void onDeath() {
        for (Drop drop : this.drops) {
            if (drop.getChance().roll()) {
                DroppedItem droppedItem = new DroppedItem(drop.createHolder());

                LastTry.entityManager.spawn(droppedItem, (int) this.physics.getCenterX(),
		            (int) this.physics.getCenterY());
            }
        }
    }

    protected void onPlayerCollision(Player player) {
        // TODO: hit the player
    }

    public int getId() {
        return this.id;
    }

    /**
     * Returns name of the enemy
     * @return String representing name of the enemy.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the spawn Weight of the enemy
     * @return int representing spawn weight of the enemy
     */
    public int getSpawnWeight(){return this.spawnWeight; }

    /**
     * Triggers the static initializer, ensuring that enemies are created.
     */
    public static void triggerEnemyCacheCreation(){return;}
}