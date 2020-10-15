package com.deploy.base.block.entity;

import com.deploy.api.net.INetwork;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public abstract class NetworkBlockEntity extends BlockEntity implements INetwork {

	public NetworkBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	@Override
	public List<Inventory> getAdjacentInventories() {
		if (this.world == null) { return new ArrayList<>(); }
		List<Inventory> out = new ArrayList<>();
		for (Direction dir: Direction.values()) {
			if (!canConnect(dir)) { continue; }
			BlockPos newPos = this.pos.add(dir.getVector());
			BlockEntity entity = this.world.getBlockEntity(newPos);
			if (entity instanceof Inventory) {
				out.add((Inventory) entity);
			}
		}
		return out;
	}

	@Override
	public List<INetwork> getAdjacentINetworks(INetwork ignore) {
		if (this.world == null) { return new ArrayList<>(); }
		List<INetwork> out = new ArrayList<>();
		for (Direction dir: Direction.values()) {
			if (!canConnect(dir)) { continue; }
			BlockPos newPos = this.pos.add(dir.getVector());
			BlockEntity entity = this.world.getBlockEntity(newPos);
			if (entity instanceof INetwork) {
				if (ignore != null && !ignore.equals(entity)) {
					out.add((INetwork) entity);
				} else if (ignore == null) {
					out.add((INetwork) entity);
				}
			}
		}
		return out;
	}

	@Override
	public abstract boolean canConnect(Direction side);
}
