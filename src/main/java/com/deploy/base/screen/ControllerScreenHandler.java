package com.deploy.base.screen;

import com.deploy.base.DeployMod;
import com.deploy.base.block.entity.ControllerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class ControllerScreenHandler extends ScreenHandler {

	ControllerBlockEntity entity;

	public ControllerScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf data) {
		this(syncId, (ControllerBlockEntity) inv.player.world.getBlockEntity(data.readBlockPos())); // haha WHAT
	}

	public ControllerScreenHandler(int syncId, ControllerBlockEntity entity) {
		super(DeployMod.CONTROLLER_SCREEN_HANDLER, syncId);
		this.entity = entity;
	}

	public ControllerBlockEntity getEntity() {
		return this.entity;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
