package com.deploy.base.screen;

import com.deploy.base.DeployMod;
import com.deploy.base.block.entity.ControllerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;

public class ControllerScreenHandler extends ScreenHandler {

	ControllerBlockEntity entity;

	public ControllerScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf data) {
		super(DeployMod.CONTROLLER_SCREEN_HANDLER, syncId);
		World world = inv.player.world;
		BlockEntity entity = world.getBlockEntity(data.readBlockPos());
		if (entity instanceof ControllerBlockEntity) {
			this.entity = (ControllerBlockEntity) entity;
		}
	}

	public ControllerScreenHandler(int syncId, ControllerBlockEntity entity) {
		super(DeployMod.CONTROLLER_SCREEN_HANDLER, syncId);

	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
