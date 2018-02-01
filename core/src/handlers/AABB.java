package handlers;

import com.badlogic.gdx.math.Vector2;

public class AABB {

	protected Vector2 pos, size;

	public AABB(Vector2 pos, Vector2 size) {
		this.pos = pos;
		this.size = size;
		pos.set(pos.x + (size.x / 2), pos.y + (size.y / 2));
		size.set(size.x / 2, size.y / 2);
	}

	public static boolean collides(AABB a, AABB b) {
		if (Math.abs(a.pos.x - b.pos.x) < a.size.x + b.size.x) {
			if (Math.abs(a.pos.y - b.pos.y) < a.size.y + b.size.y) {
				return true;
			}
		}

		return false;
	}

	public static boolean inside(AABB a, Vector2 b) {
		if (Math.abs(a.pos.x - b.x) < a.size.x) {
			if (Math.abs(a.pos.y - b.y) < a.size.y) {
				return true;
			}
		}
		return false;
	}
}
