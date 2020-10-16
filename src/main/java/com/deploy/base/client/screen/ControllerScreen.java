package com.deploy.base.client.screen;

import com.deploy.api.net.INetwork;
import com.deploy.base.block.entity.ControllerBlockEntity;
import com.deploy.base.screen.ControllerScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ControllerScreen extends HandledScreen<ControllerScreenHandler> {

	private static final Identifier TEXTURE = new Identifier("textures/gui/advancements/window.png");
	private static final Identifier BACMTEXTURE = new Identifier("textures/gui/advancements/window.png");

	private static final VertexConsumerProvider.Immediate IMMEDIATE = VertexConsumerProvider.immediate(
			RenderLayer.getBlockLayers().stream().collect(
					Collectors.toMap(
							(renderLayer) -> renderLayer,
							(renderLayer) -> new BufferBuilder(renderLayer.getExpectedBufferSize())
					)
			),
			new BufferBuilder(256));

	private final ControllerBlockEntity entity;
	private Random rand;

	public ControllerScreen(ControllerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.entity = handler.getEntity();
		this.backgroundWidth = 252;
		this.backgroundHeight = 140;
	}

	public void init() {
		rand = new Random();
		rand.setSeed(42L);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		assert client != null;
		client.getTextureManager().bindTexture(TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		this.drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
		assert client != null;
		ItemRenderer renderer = client.getItemRenderer();
		int x = width / 2 - 8;
		int y = height / 2 - 8;

		BlockRenderManager manager = client.getBlockRenderManager();
		matrices.push();
		matrices.translate((float)x, (float)y, 100.0F);
		matrices.multiply(new Quaternion(Vector3f.POSITIVE_X, -35, true));
		matrices.multiply(new Quaternion(Vector3f.POSITIVE_Y, 135, true));
		matrices.scale(1, -1, 1);
		matrices.scale(16, 16, 16);
		this.renderBlock(matrices, entity);
		for (INetwork net: entity.getConnectedINetworks()) {
			if (net instanceof BlockEntity) {
				BlockEntity netEntity = (BlockEntity) net;
				this.renderBlock(matrices, netEntity);
			}
		}
		for (Inventory inv: entity.getConnectedInventories()) {
			if (inv instanceof BlockEntity) {
				BlockEntity invEntity = (BlockEntity) inv;
				this.renderBlock(matrices, invEntity);
			}
		}
		IMMEDIATE.draw();
		matrices.pop();
	}

	private void renderBlock(MatrixStack matrices, BlockEntity netEntity) {
		BlockState block = netEntity.getCachedState();
		RenderLayer layer = RenderLayers.getBlockLayer(block);
		VertexConsumer consumer = IMMEDIATE.getBuffer(layer);
		BlockPos diff = netEntity.getPos().subtract(entity.getPos());
		matrices.push();
		matrices.translate(diff.getX(), diff.getY(), diff.getZ());
		if (block.getRenderType() == BlockRenderType.ENTITYBLOCK_ANIMATED) {
			BlockEntityRenderer<BlockEntity> renderer = BlockEntityRenderDispatcher.INSTANCE.get(netEntity);
			if (renderer != null) {
				renderer.render(netEntity, client.getTickDelta(), matrices, IMMEDIATE, 15728880, OverlayTexture.DEFAULT_UV);
			}
		} else if (block.getRenderType() == BlockRenderType.MODEL) {
			client.getBlockRenderManager().renderBlock(block, diff, client.world, matrices, consumer, true, rand);
		}
		matrices.pop();
	}

	@Environment(EnvType.CLIENT)
	public static class ControllerWidget extends DrawableHelper {

		private ControllerWidget parent;


	}
}

