package com.deploy.base.block.entity;

import com.deploy.base.DeployMod;

import com.deploy.base.screen.ControllerScreenHandler;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
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
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControllerBlockEntity extends NetworkBlockEntity implements BlockEntityClientSerializable,
		ExtendedScreenHandlerFactory, Nameable {

	private Text customName;

	public ControllerBlockEntity() {
		super(DeployMod.CONTROLLER_ENTITY);
	}

	@Override
	public boolean canConnect(Direction side) {
		return true;
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		super.toTag(tag);
		if (this.customName != null) {
			tag.putString("CustomName", Text.Serializer.toJson(this.customName));
		}
		return tag;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		if (tag.contains("CustomName", 8)) {
			this.customName = Text.Serializer.fromJson(tag.getString("CustomName"));
		}
	}

	@Override
	public void fromClientTag(CompoundTag tag) {
		fromTag(getCachedState(), tag);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag) {
		return toTag(tag);
	}

	public void setCustomName(Text customName) {
		this.customName = customName;
	}

	public Text getName() {
		return this.customName != null ? this.customName : new TranslatableText("screen.deploy.controller");
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
		packetByteBuf.writeBlockPos(this.pos);
	}

	@Override
	public Text getDisplayName() {
		return this.getName();
	}

	@Nullable
	public Text getCustomName() {
		return this.customName;
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new ControllerScreenHandler(syncId, this);
	}
}
