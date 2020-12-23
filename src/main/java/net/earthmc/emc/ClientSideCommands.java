package net.earthmc.emc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;

import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ClientSideCommands implements ClientCommandPlugin 
{

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher)
    {
        dispatcher.register(ArgumentBuilders.literal("townless").executes
        (            
            source -> 
            {
                final JsonArray townless = EMCMod.townless;
                String townlessString = "";
                Formatting headingFormatting = Formatting.byName(EMCMod.config.townless.headingTextColour);
                Formatting playerNameFormattting = Formatting.byName(EMCMod.config.townless.playerTextColour);

                for (int i = 0; i < townless.size(); i++)
                {
                    JsonObject currentPlayer = (JsonObject) townless.get(i);
                    townlessString += currentPlayer.get("name").getAsString() + " ";
                }

                source.getSource().sendFeedback(new TranslatableText("Townless Players [" + townless.size() + "]").formatted(headingFormatting));
                source.getSource().sendFeedback(new TranslatableText(townlessString).formatted(playerNameFormattting));
                return 1;
            }
        ));

        dispatcher.register(ArgumentBuilders.literal("townless")
            .then(ArgumentBuilders.literal("copyNames").executes
            (
                source -> 
                {
                    final JsonArray townless = EMCMod.townless;
                    String townlessString = "";

                    for (int i = 0; i < townless.size(); i++)
                    {
                        JsonObject currentPlayer = (JsonObject) townless.get(i);
                        townlessString += currentPlayer.get("name").getAsString() + " ";
                    }

                    EMCMod.client.keyboard.setClipboard(townlessString);
                    source.getSource().sendFeedback(new TranslatableText("Copied townless players to clipboard!"), true);
                    return 1;
                }
            ))
        );
    }
    
}
