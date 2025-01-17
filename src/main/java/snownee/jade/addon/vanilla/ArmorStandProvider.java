package snownee.jade.addon.vanilla;

import mcp.mobius.waila.api.EntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import mcp.mobius.waila.api.ui.IElementHelper;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import snownee.jade.Jade;
import snownee.jade.VanillaPlugin;

public class ArmorStandProvider implements IEntityComponentProvider {

	public static final ArmorStandProvider INSTANCE = new ArmorStandProvider();

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
		if (!config.get(VanillaPlugin.ARMOR_STAND)) {
			return;
		}
		ArmorStand entity = (ArmorStand) accessor.getEntity();
		IItemHandler itemHandler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		if (itemHandler == null) {
			return;
		}
		IElementHelper helper = tooltip.getElementHelper();
		for (int i = itemHandler.getSlots() - 1; i >= 0; i--) {
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack.isEmpty())
				continue;
			tooltip.add(helper.item(stack, 0.75f));
			tooltip.append(helper.text(stack.getHoverName()).translate(Jade.VERTICAL_OFFSET));
		}
	}

}
