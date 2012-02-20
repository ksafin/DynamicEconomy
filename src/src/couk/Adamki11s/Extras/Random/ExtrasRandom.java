package couk.Adamki11s.Extras.Random;

import java.util.List;
import java.util.Random;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ExtrasRandom extends RandomMethods {

	private CreatureType[] creatures = {CreatureType.CHICKEN, CreatureType.COW, CreatureType.CREEPER, CreatureType.GHAST, CreatureType.GIANT,
			CreatureType.MONSTER, CreatureType.PIG, CreatureType.PIG_ZOMBIE, CreatureType.SHEEP, CreatureType.SKELETON,
			CreatureType.SLIME, CreatureType.SPIDER, CreatureType.SQUID, CreatureType.WOLF, CreatureType.ZOMBIE};

	@Override
	public int getRandomInt(int upperBound, int lowerBound) {
		Random r = new Random();
		return r.nextInt(upperBound) + lowerBound;
	}

	@Override
	public double getRandomDouble(double upperBound, double lowerBound) {
		Random r = new Random();
		return (double)((r.nextInt((int) upperBound) + (int)lowerBound) + r.nextDouble()); 
	}

	@Override
	public float getRandomFloat(float upperBound, float lowerBound) {
		Random r = new Random();
		return (float)((r.nextInt((int) upperBound) + (int)lowerBound) + r.nextFloat());
	}

	@Override
	public CreatureType getRandomCreature() {
		int random = getRandomInt(15, 1);
		return creatures[random];
	}

	@Override
	public Entity getRandomLivingEntityFromWorld(World w) {
		int entities = w.getEntities().size();
		List<Entity> ents = w.getEntities();
		Entity e = null;
		while(e == null || !(e instanceof LivingEntity)){
			e = ents.get(getRandomInt(entities, 1));
		}
		return e;
	}

	@Override
	public Player getRandomPlayer(Server s) {
		return s.getOnlinePlayers()[getRandomInt(s.getOnlinePlayers().length - 1, 0)];
	}

	@Override
	public int getRandomBlockId() {
		int id = -1;
		while(!(id >= 0 && id <= 96) && !(id >= 256 && id <= 359) && !(id == 2256 || id == 2257)){
			id = getRandomInt(2257, 0);
		}
		return id;
	}

}
