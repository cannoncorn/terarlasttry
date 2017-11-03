package org.egordorichev.lasttry.entity.entities.world;

import com.badlogic.gdx.Gdx;
import org.egordorichev.lasttry.Globals;
import org.egordorichev.lasttry.entity.Entity;
import org.egordorichev.lasttry.entity.asset.Assets;
import org.egordorichev.lasttry.entity.component.RenderComponent;
import org.egordorichev.lasttry.entity.component.UpdateComponent;
import org.egordorichev.lasttry.entity.entities.item.tile.Block;
import org.egordorichev.lasttry.entity.entities.item.tile.Wall;
import org.egordorichev.lasttry.entity.entities.world.chunk.Chunk;
import org.egordorichev.lasttry.entity.entities.world.chunk.ChunkIO;
import org.egordorichev.lasttry.util.log.Log;

/**
 * Handles chunks
 */
public class World extends Entity {
	/**
	 * Stores info about world type and size
	 */
	public WorldInfo info;
	/**
	 * Handles chunks
	 */
	private Chunk[] chunks;

	public World(WorldInfo info) {
		this.info = info;
		this.chunks = new Chunk[info.getWidth() * info.getHeight()];

		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				if (Math.random() > 0.5) {
					this.setBlock("lt:dirt", (short) x, (short) y);
				}
			}
		}

		this.addComponent(UpdateComponent.class);
		this.addComponent(RenderComponent.class);
	}

	@Override
	public void update(float delta) {
		// TODO
	}

	@Override
	public void render() {
		short xStart = (short) Math.floor(Globals.camera.getX() / Block.SIZE);
		short yStart = (short) Math.floor(Globals.camera.getY() / Block.SIZE);
		short width = (short) (Math.floor(Gdx.graphics.getWidth() / Block.SIZE) + 1);
		short height = (short) (Math.floor(Gdx.graphics.getHeight() / Block.SIZE) + 1);

		for (short x = xStart; x < xStart + width; x++) {
			for (short y = yStart; y < yStart + height; y++) {
				String id = this.getWall(x, y);

				if (id != null) {
					Wall wall = (Wall) Assets.items.get(id);
					wall.render();
				}

				id = this.getBlock(x, y);

				if (id != null) {
					Block block = (Block) Assets.items.get(id);
					block.render(x, y);
				}
			}
		}
	}

	/**
	 * Returns block ID at given position in world
	 *
	 * @param x Block X
	 * @param y Block Y
	 * @return Block ID at given position
	 */
	public String getBlock(short x, short y) {
		if (this.isOut(x, y)) {
			return null;
		}

		Chunk chunk = this.getChunkFor(x, y);

		if (chunk == null) {
			return null;
		} else {
			return chunk.getBlock(chunk.toRelativeX(x), chunk.toRelativeY(y));
		}
	}

	/**
	 * Sets block ID at given position in world
	 *
	 * @param value New block ID
	 * @param x Block X
	 * @param y Block Y
	 */
	public void setBlock(String value, short x, short y) {
		if (this.isOut(x, y)) {
			return;
		}

		Chunk chunk = this.getChunkFor(x, y);

		if (chunk != null) {
			chunk.setBlock(value, chunk.toRelativeX(x), chunk.toRelativeY(y));
		}
	}

	/**
	 * Returns wall ID at given position in world
	 *
	 * @param x Wall X
	 * @param y Wall Y
	 * @return Wall ID at given position
	 */
	public String getWall(short x, short y) {
		if (this.isOut(x, y)) {
			return null;
		}

		Chunk chunk = this.getChunkFor(x, y);

		if (chunk == null) {
			return null;
		} else {
			return chunk.getWall(chunk.toRelativeX(x), chunk.toRelativeY(y));
		}
	}

	/**
	 * Sets wall ID at given position in world
	 *
	 * @param value New wall ID
	 * @param x Wall X
	 * @param y Wall Y
	 */
	public void setWall(String value, short x, short y) {
		if (this.isOut(x, y)) {
			return;
		}

		Chunk chunk = this.getChunkFor(x, y);

		if (chunk != null) {
			chunk.setWall(value, chunk.toRelativeX(x), chunk.toRelativeY(y));
		}
	}

	/**
	 * Returns data at given position in world
	 *
	 * @param x Data X
	 * @param y Data Y
	 * @return Data at given position
	 */
	public short getData(short x, short y) {
		if (this.isOut(x, y)) {
			return 0;
		}

		Chunk chunk = this.getChunkFor(x, y);

		if (chunk == null) {
			return 0;
		} else {
			return chunk.getData(chunk.toRelativeX(x), chunk.toRelativeY(y));
		}
	}

	/**
	 * Sets data at given position in world
	 *
	 * @param value New data
	 * @param x Data X
	 * @param y Data Y
	 */
	public void setData(short value, short x, short y) {
		if (this.isOut(x, y)) {
			return;
		}

		Chunk chunk = this.getChunkFor(x, y);

		if (chunk != null) {
			chunk.setData(value, chunk.toRelativeX(x), chunk.toRelativeY(y));
		}
	}


	/**
	 * Returns chunk array index, containing given block
	 *
	 * @param x Block X
	 * @param y Block Y
	 * @return Chunk array index, containing given block (unsafe!)
	 */
	private int getChunkIndex(int x, int y) {
		return x + y * this.info.getWidth();
	}

	/**
	 * Returns chunk, that contains given block
	 *
	 * @param x Block X
	 * @param y Block Y
	 * @return The chunk
	 */
	private Chunk getChunkFor(int x, int y) {
		int index = this.getChunkIndex((int) Math.floor(x / Chunk.SIZE), (int) Math.floor(y / Chunk.SIZE));

		if (index < 0 || index > this.chunks.length - 1) {
			return null;
		}

		if (this.chunks[index] == null) {
			short cx = (short) Math.floor(x / Chunk.SIZE);
			short cy = (short) Math.floor(y / Chunk.SIZE);

			Log.debug("Loading chunk " + cx + ":" + cy);
			this.loadChunk(cx, cy);

			return this.chunks[index];
		}

		return this.chunks[index];
	}

	/**
	 * Returns true, if given position is outside of block array
	 *
	 * @param x Block X
	 * @param y Block Y
	 * @return True, if given position is outside of block array
	 */
	private boolean isOut(short x, short y) {
		return x < 0 || y < 0 || x > this.info.getWidth() * Chunk.SIZE - 1 || y > this.info.getHeight() * Chunk.SIZE -1;
	}

	private void loadChunk(short x, short y) {
		this.chunks[this.getChunkIndex(x, y)] = ChunkIO.load(x, y);
	}
}