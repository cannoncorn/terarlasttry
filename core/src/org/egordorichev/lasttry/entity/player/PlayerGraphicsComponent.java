package org.egordorichev.lasttry.entity.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.egordorichev.lasttry.entity.components.*;
import org.egordorichev.lasttry.entity.player.skin.*;
import org.egordorichev.lasttry.graphics.Animation;
import org.egordorichev.lasttry.graphics.AnimationFrame;

public class PlayerGraphicsComponent extends CreatureGraphicsComponent {
	private Animation[] animations = new Animation[CreatureStateComponent.State.values().length];
	private Texture texture;

	public PlayerGraphicsComponent() {
		this.setupAnimations();

		PlayerRenderInfo info = new PlayerRenderInfo(1, Color.GREEN, Color.GREEN, Color.GREEN, 1, true); // TODO: replace it

		this.texture = PlayerRenderer.generateTexture(info);
	}

	@Override
	public void render() {
		this.animations[this.creature.state.get().getID()].render(
			this.creature.physics.getPosition().x, this.creature.physics.getPosition().y,
			this.creature.physics.getSize().x, this.creature.physics.getSize().y,
			(this.creature.physics.getDirection() == PhysicsComponent.Direction.LEFT), false);
	}

	private void setupAnimations() {
		Animation idleAnimation = new Animation(false);
		idleAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 8, 32, 48), 0));

		Animation movingAnimation = new Animation(true);
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 342, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 400, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 456, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 512, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 568, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 624, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 680, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 736, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 792, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 848, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 902, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 960, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 1016, 32, 48), 1));
		movingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 1072, 32, 48), 1));

		Animation jumpingAnimation = new Animation(false);
		jumpingAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 4, 288, 32, 48), 0));

		Animation flyingAnimation = new Animation(true); // TODO

		Animation deadAnimation = new Animation(false);
		deadAnimation.addFrame(new AnimationFrame(new TextureRegion(this.texture, 0, 0, 0, 0), 0)); // TODO

		Animation actingAnimation = new Animation(true); // TODO

		this.animations[CreatureStateComponent.State.IDLE.getID()] = idleAnimation;
		this.animations[CreatureStateComponent.State.MOVING.getID()] = movingAnimation;
		this.animations[CreatureStateComponent.State.JUMPING.getID()] = jumpingAnimation;
		this.animations[CreatureStateComponent.State.FALLING.getID()] = jumpingAnimation; // They are the same
		this.animations[CreatureStateComponent.State.FLYING.getID()] = flyingAnimation;
		this.animations[CreatureStateComponent.State.DEAD.getID()] = deadAnimation;
		this.animations[CreatureStateComponent.State.ACTING.getID()] = actingAnimation;
	}
}