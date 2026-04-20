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

        // Ensure correct packet type
        val input = packet as? PlayerAuthInputPacket ?: return true

        val player = session.localPlayer ?: return true
        val world = session.world ?: return true

        val pos = player.position

        val x = pos.x.toInt()
        val y = pos.y.toInt() - 1
        val z = pos.z.toInt()

        // Safety check
        val blockBelow = world.getBlock(x, y, z)
        if (!blockBelow.isAir) return true

        // IMPORTANT: avoid assumptions about API behavior
        runCatching {
            session.useItem()
        }

        return true
    }

    override fun afterPacketBound(packet: BedrockPacket) {
        // no-op
    }
    }
