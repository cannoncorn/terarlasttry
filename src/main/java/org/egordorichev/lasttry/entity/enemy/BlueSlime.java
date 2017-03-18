package org.egordorichev.lasttry.entity.enemy;

import org.egordorichev.lasttry.LastTry;
import org.egordorichev.lasttry.entity.Drop;
import org.egordorichev.lasttry.entity.EnemyID;
import org.egordorichev.lasttry.item.Item;
import org.egordorichev.lasttry.graphics.Assets;

public class BlueSlime extends Slime {
	public BlueSlime() {
		super(EnemyID.blueSlime, LastTry.world.isExpert() ? 50 : 25, 2, LastTry.world.isExpert() ? 7 : 14,
			Assets.blueSlimeTexture);

		this.drops.add(new Drop(Item.copperCoin, Drop.Chance.ALWAYS, 25, 25));
	}
}
