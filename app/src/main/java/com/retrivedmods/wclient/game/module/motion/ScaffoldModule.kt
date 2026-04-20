package com.retrivedmods.wclient.game.module.motion

import com.retrivedmods.wclient.game.Module
import com.retrivedmods.wclient.game.ModuleCategory
import com.retrivedmods.wclient.game.ComposedPacketHandler
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket

class ScaffoldModule : Module("Scaffold", ModuleCategory.Motion),
    ComposedPacketHandler {

    override fun beforePacketBound(packet: BedrockPacket): Boolean {
        if (!isEnabled) return true

        // Only run on movement/input packets
        if (packet !is PlayerAuthInputPacket) return true

        val player = session.localPlayer ?: return true
        val world = session.world ?: return true

        val pos = player.position

        val x = pos.x.toInt()
        val y = (pos.y - 1).toInt()
        val z = pos.z.toInt()

        val blockBelow = world.getBlock(x, y, z)

        // If block already exists, do nothing
        if (!blockBelow.isAir) return true

        runCatching {
            session.useItem()
        }

        return true
    }

    override fun afterPacketBound(packet: BedrockPacket) {
        // optional cleanup (not needed)
    }
}
