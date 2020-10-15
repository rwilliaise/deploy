package com.deploy.base.block.entity;

import com.deploy.base.DeployMod;
import net.minecraft.util.math.Direction;

public class CableBlockEntity extends NetworkBlockEntity {

	public CableBlockEntity() {
		super(DeployMod.CABLE_ENTITY);
	}

	@Override
	public boolean canConnect(Direction side) {
		return true;
	}
}
