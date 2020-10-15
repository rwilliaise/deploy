package com.deploy.base.block.entity;

import com.deploy.base.DeployMod;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerBlockEntity extends NetworkBlockEntity implements NamedScreenHandlerFactory,
		ExtendedScreenHandlerFactory, BlockEntityClientSerializable {

	public ControllerBlockEntity() {
		super(DeployMod.CONTROLLER_ENTITY);
	}

	@Override
	public boolean canConnect(Direction side) {
		return true;
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText("screen.deploy.controller");
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return null;
	}

	@Override
	public void fromClientTag(CompoundTag compoundTag) {

	}

	@Override
	public CompoundTag toClientTag(CompoundTag compoundTag) {
		return null;
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {

	}
}
