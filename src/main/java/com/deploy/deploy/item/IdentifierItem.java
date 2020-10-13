package com.deploy.deploy.item;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class IdentifierItem extends Item {

	public IdentifierItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		if (!world.isRemote) { return ActionResultType.PASS; }
		BlockState state = world.getBlockState(context.getPos());
		StringBuilder out = new StringBuilder();
		out.append("ID: ( ");
		out.append(state.getBlock().getRegistryName().toString());
		out.append(" )   States: { ");
		boolean[] comma = { false }; // wtf
		state.getValues().forEach((p, c) -> {
			if (comma[0]) {
				out.append(", ");
			} else {
				comma[0] = true;
			}
			out.append(p.getName());
			out.append(": ");
			out.append(c.toString());
		});
		BlockPos pos = context.getPos();
		out.append(" }   Pos: { ");
		out.append(pos.getX());
		out.append(", ");
		out.append(pos.getY());
		out.append(", ");
		out.append(pos.getZ());
		out.append(" }");
		context.getPlayer().sendMessage(new StringTextComponent(out.toString()), context.getPlayer().getUniqueID());
		return ActionResultType.SUCCESS;
	}
}
