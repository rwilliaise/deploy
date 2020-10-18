package com.deploy.base.block;

import com.deploy.api.net.INetwork;
import com.deploy.base.block.entity.CableBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class CableBlock extends ConnectingBlock implements BlockEntityProvider {

	public CableBlock() {
		super(0.1875f, FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES));
		this.setDefaultState(getStateManager().getDefaultState()
				.with(NORTH, false)
				.with(SOUTH, false)
				.with(WEST, false)
				.with(EAST, false)
				.with(UP, false)
				.with(DOWN, false));
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(this)) {
			return true;
		}

		return super.isSideInvisible(state, stateFrom, direction);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.withConnectionProperties(ctx.getWorld(), ctx.getBlockPos());
	}

	public BlockState withConnectionProperties(BlockView world, BlockPos pos) {
		BlockEntity block = world.getBlockEntity(pos.down());
		BlockEntity block2 = world.getBlockEntity(pos.up());
		BlockEntity block3 = world.getBlockEntity(pos.north());
		BlockEntity block4 = world.getBlockEntity(pos.east());
		BlockEntity block5 = world.getBlockEntity(pos.south());
		BlockEntity block6 = world.getBlockEntity(pos.west());
		return this.getDefaultState()
				.with(DOWN, block instanceof INetwork || block instanceof Inventory)
				.with(UP, block2 instanceof INetwork || block2 instanceof Inventory)
				.with(NORTH, block3 instanceof INetwork || block3 instanceof Inventory)
				.with(EAST, block4 instanceof INetwork || block4 instanceof Inventory)
				.with(SOUTH, block5 instanceof INetwork || block5 instanceof Inventory)
				.with(WEST, block6 instanceof INetwork || block6 instanceof Inventory);
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState,
												WorldAccess world, BlockPos pos, BlockPos posFrom) {
		boolean bl = newState.getBlock() == this
				|| world.getBlockEntity(posFrom) instanceof INetwork
				|| world.getBlockEntity(posFrom) instanceof Inventory;
		return state.with(FACING_PROPERTIES.get(direction), bl);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(NORTH, SOUTH, WEST, EAST, UP, DOWN);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockView world) {
		return new CableBlockEntity();
	}

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}
}
