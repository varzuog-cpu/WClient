package com.retrivedmods.wclient.game.module.motion

import com.retrivedmods.wclient.game.InterceptablePacket
import com.retrivedmods.wclient.game.Module
import com.retrivedmods.wclient.game.ModuleCategory
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket

class ScaffoldModule : Module("scaffold", ModuleCategory.Motion) {

    override fun beforePacketBound(interceptablePacket: InterceptablePacket) {
        if (!isEnabled) return

        val packet = interceptablePacket.packet
        if (packet !is PlayerAuthInputPacket) return

        val player = session.localPlayer ?: return
        val world = session.world ?: return

        val x = player.position.x.toInt()
        val y = (player.position.y - 1).toInt()
        val z = player.position.z.toInt()

        if (!world.getBlock(x, y, z).isAir) return

        runCatching {
            session.useItem()
        }
    }
}
