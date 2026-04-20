package com.retrivedmods.wclient.game.module.motion

import com.retrivedmods.wclient.game.InterceptablePacket
import com.retrivedmods.wclient.game.Module
import com.retrivedmods.wclient.game.ModuleCategory
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket

class ScaffoldModule : Module("Scaffold", ModuleCategory.Motion) {

    override fun beforePacketBound(interceptablePacket: InterceptablePacket) {
        if (!isEnabled) return

        val packet = interceptablePacket.packet
        if (packet !is PlayerAuthInputPacket) return

        val player = session.localPlayer ?: return
        val world = session.world ?: return

        val pos = player.position

        val x = pos.x.toInt()
        val y = (pos.y - 1).toInt()
        val z = pos.z.toInt()

        val blockBelow = world.getBlock(x, y, z)
        if (!blockBelow.isAir) return

        runCatching {
            session.useItem()
        }
    }
}
