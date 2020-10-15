package com.deploy.base.net;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static com.deploy.base.DeployMod.MOD_ID;

public class DeployNet {

	public static final Identifier CONTROLLER_CHANGE = new Identifier(MOD_ID, "controller_change");

	public static void onServerInitialize() {
		ServerSidePacketRegistry.INSTANCE.register(CONTROLLER_CHANGE, DeployNet::handleControllerChange);
	}

	public static void sendControllerChange(BlockPos pos) {
		PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
		passedData.writeBlockPos(pos);
		ClientSidePacketRegistry.INSTANCE.sendToServer(CONTROLLER_CHANGE, passedData);
	}

	public static void handleControllerChange(PacketContext context, PacketByteBuf data) {
		BlockPos pos = data.readBlockPos();

		context.getTaskQueue().execute(() -> {
			if (context.getPlayer().world.getBlockEntity(pos) != null) {

			}
		});
	}
}
