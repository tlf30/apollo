import org.apollo.cache.def.ItemDefinition
import org.apollo.game.action.DistancedAction
import org.apollo.game.message.impl.NpcActionMessage
import org.apollo.game.model.Position
import org.apollo.game.model.entity.Player
import org.apollo.game.model.entity.Skill
import org.apollo.plugin.skills.fishing.*
import kotlin.properties.Delegates
import java.util.Random

class FishingAction: DistancedAction<Player> {

    var spot: FishingSpot by Delegates.notNull()
    var tool: FishingTool by Delegates.notNull()
    var started: Boolean = false
    var options: IntArray by Delegates.notNull()
    var minLevel: Int = 110

    constructor(mob: Player, position: Position, spot: FishingSpot, option: Int) : super(4, true, mob, position, 1) {
        this.tool = FISHING_TOOLS[spot.tools[option - 1]]!!
        this.spot = spot
        this.options = if (option == 1) spot.firstFish else spot.secondFish
        //Get min level in really compact way
        options
                .asSequence()
                .filter { minLevel > FISH[it]!!.level }
                .forEach { minLevel = FISH[it]!!.level }
        //
    }

    fun successfulCatch(level: Int, req: Int): Boolean {
        return intArrayOf(level - req + 5, 30).min()!! > Random().nextInt(40)
    }

    fun startFishing() {
        started = true
        mob.sendMessage(tool.message)
    }

    fun findBait(): Int {
        if (tool.bait == -1) {
            return -1;
        }
        if (mob.inventory.contains(tool.bait)) {
            return tool.bait
        }
        return -1;
    }

    override fun stop() {
        super.stop()
        mob.stopAnimation()
    }

    override fun executeAction() {
        mob.turnTo(position)

        //Check levels
        if (minLevel > mob.skillSet.getSkill(Skill.FISHING).currentLevel) {
            mob.sendMessage("You need a fishing level of " + minLevel + " to fish at this spot.")
            stop()
            return
        }

        //Check inv capacity
        if (mob.inventory.freeSlots() == 0) {
            mob.inventory.forceCapacityExceeded()
            stop()
            return
        }

        //check bait
        val bait = findBait();
        if (findBait() == -1) {
            mob.sendMessage("You need " + ItemDefinition.lookup(bait).name.toLowerCase() + "s to fish at this spot.")
            stop()
            return
        }

        //Do action
        if (started) {
            //Get fish
            var fishList = ArrayList<Fish>()
            for (option in options) {
                var f = FISH[option]!!
                if (f.level <= mob.skillSet.getSkill(Skill.FISHING).currentLevel) {
                    fishList.add(f)
                }
            }

            var fish:Fish by Delegates.notNull()
            if (fishList.size == 1) {
                fish = fishList.get(0)
            } else {

                //Get 70/30 chance
                if (Random().nextInt(100) > 70) {
                    fish = fishList.get(0)
                } else {
                    fish = fishList.get(1)
                }
            }

            //Perform catch
            if (successfulCatch(mob.skillSet.getSkill(Skill.FISHING).currentLevel, fish.level)) {
                if (bait != -1) mob.inventory.remove(bait)
                mob.inventory.add(fish.id)
                var name = if (fish.name.endsWith("s")) "some " + fish.name else "a " + fish.name
                mob.sendMessage("You catch " + name + ".")
                mob.skillSet.addExperience(Skill.FISHING, fish.exp)
                //Check bait for continued fishing
                if (findBait() == -1) {
                    mob.sendMessage("You need more " + ItemDefinition.lookup(bait).name.toLowerCase() + "s to fish at this spot.")
                    stop()
                    return
                }
            }

        } else {
            startFishing()
        }
        mob.playAnimation(tool.animation)
    }
}

on {NpcActionMessage::class}
        .then {
            if (FISHING_SPOTS.contains(it.world.npcRepository[index].id)) {
                it.startAction(FishingAction(it, it.world.npcRepository[index].position, FISHING_SPOTS.get(it.world.npcRepository[index].id)!!, option))
                this.terminate()
            }
        }

