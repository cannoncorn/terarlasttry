package org.egordorichev.lasttry.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.egordorichev.lasttry.Globals;
import org.egordorichev.lasttry.entity.components.*;
import org.egordorichev.lasttry.player.skin.*;
import org.egordorichev.lasttry.graphics.AnimationFrame;
import org.egordorichev.lasttry.inventory.ItemHolder;

public class PlayerGraphicsComponent extends CreatureGraphicsComponent {
	/**
	 * Player texture
	 */
	private TextureRegion texture;

	public PlayerGraphicsComponent() {
		PlayerRenderInfo info = new PlayerRenderInfo(1, new Color(1, 0, 1, 1),
			new Color(1, 0, 1, 1), new Color(1, 1, 0, 1), 1, true); // TODO: load it from file

		this.texture = PlayerRenderer.generateTextureRegion(info);
		this.setupAnimations();
	}

	@Override
	public void render() {
		ItemHolder holder = Globals.getPlayer().getInventory().getActiveItem();

		if (holder.getItem() != null) {
			holder.getItem().renderAnimation();
		}

		this.animations[this.creature.state.get().getID()].render(
			this.creature.physics.getPosition().x, this.creature.physics.getPosition().y,
			this.creature.physics.getSize().x, this.creature.physics.getSize().y,
			(this.creature.physics.getDirection() == PhysicsComponent.Direction.LEFT), false);
	}

	/**
	 * Sets up animations
	 */
	private void setupAnimations() {
		this.animations[CreatureStateComponent.State.IDLE.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 8, 32, 48), 0));

		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 342, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 400, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 456, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 512, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 568, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 624, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 680, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 736, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 792, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 848, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 902, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 960, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 1016, 32, 48), 1));
		this.animations[CreatureStateComponent.State.MOVING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 1072, 32, 48), 1));

		this.animations[CreatureStateComponent.State.JUMPING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 288, 32, 48), 0));
		this.animations[CreatureStateComponent.State.FALLING.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 288, 32, 48), 0));

		this.animations[CreatureStateComponent.State.DEAD.getID()].addFrame(new AnimationFrame(new TextureRegion(this.texture, 0, 0, 0, 0), 0)); // TODO
	}
}