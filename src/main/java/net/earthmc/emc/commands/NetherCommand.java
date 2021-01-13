package net.earthmc.emc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.earthmc.emc.EMCMod;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class NetherCommand 
{
    public static void register(CommandDispatcher<CottonClientCommandSource> dispatcher)
    {
        dispatcher.register(
        ArgumentBuilders.literal("nether").then(
            ArgumentBuilders.argument("x", IntegerArgumentType.integer()).then(
                ArgumentBuilders.argument("z", IntegerArgumentType.integer()).executes(c -> {

                int x = IntegerArgumentType.getInteger(c, "x");
                int z = IntegerArgumentType.getInteger(c, "z");
                c.getSource().sendFeedback(new TranslatableText("EMCE > Nether coordinates for " + x + ", " + z + ": " + x / 8 + ", " + z / 8).formatted(Formatting.byName("AQUA")));
                return 1;

            })
        ).executes(c -> {
            c.getSource().sendFeedback(new TranslatableText("EMCE > Not enough arguments! (x + z)").formatted(Formatting.byName("RED")));
            return 1;
        })).executes(c ->
        {
            int x;
            int z;

            if (EMCMod.client.player != null)
            {
                x = (int) EMCMod.client.player.getX();
                z = (int) EMCMod.client.player.getZ();

                // TODO: figure out a way to use formatting codes for colors.
                c.getSource().sendFeedback(new TranslatableText("EMCE > No coordinates specified, using your own instead.").formatted(Formatting.byName("RED")));
                c.getSource().sendFeedback(new TranslatableText("EMCE > Nether coordinates for " + x + ", " + z + ": " + x / 8 + ", " + z / 8).formatted(Formatting.byName("AQUA")));
            }
            else {
                c.getSource().sendFeedback(new TranslatableText("EMCE > Unable to use your own coordiantes! This is rare.").formatted(Formatting.byName("RED")));
            }

            return 1;
        }));
    }
}