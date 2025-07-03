/*
 * Copyright (c) 2024-2025 balugaq
 *
 * This file is part of JustEnoughGuide, available under MIT license.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * - The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 * - The author's name (balugaq or 大香蕉) and project name (JustEnoughGuide or JEG) shall not be
 *   removed or altered from any source distribution or documentation.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.balugaq.jeg.core.commands;

import com.balugaq.jeg.api.interfaces.JEGCommand;
import com.balugaq.jeg.utils.ClipboardUtil;
import com.balugaq.jeg.utils.compatibility.Converter;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the "/jeg help" command.
 * It shows the list of available commands and their usage.
 * <p>
 * This command is also the default command when no other command is specified.
 *
 * @author balugaq
 * @since 1.8
 */
@SuppressWarnings({"ClassCanBeRecord", "SwitchStatementWithTooFewBranches"})
@Getter
public class CategoriesCommand implements JEGCommand {
    private final Plugin plugin;

    public CategoriesCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull List<String> onTabCompleteRaw(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        switch (args.length) {
            case 1 -> {
                return List.of("categories");
            }

            default -> {
                return List.of();
            }
        }
    }

    @Override
    public boolean canCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                return "categories".equalsIgnoreCase(args[0]);
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onCommand(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            ChestMenu menu = new ChestMenu("&6物品组大全");
            menu.setSize(54);

            populateCategoryMenu(menu, Slimefun.getRegistry().getAllItemGroups(), 1, player);

            menu.setPlayerInventoryClickable(false);
            menu.open(player);
        } else {
            sender.sendMessage(Slimefun.getLocalization().getMessage("messages.only-players"));
        }
    }

    /**
     * Populates the category gui. 45 items per page.
     * @param menu the SCMenu to populate
     * @param groups the List of itemgroups
     * @param page the page number
     * @param p the player that will be viewing this menu
     */
    @SuppressWarnings("deprecation")
    @ParametersAreNonnullByDefault
    private static void populateCategoryMenu(ChestMenu menu, List<ItemGroup> groups, @Range(from = 1, to = Integer.MAX_VALUE) int page, Player p) {
        for (int i = 0; i < 54; i++) {
            menu.addMenuClickHandler(i, ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i = 45; i < 54; i++) {
            menu.replaceExistingItem(i, ChestMenuUtils.getBackground());
        }

        for (int i = 0; i < 45; i++) {
            int groupIndex = i + 1 + (page - 1) * 45;
            ItemGroup group = getItemGroupOrNull(groups, groupIndex);
            if (group != null) {
                ItemStack catItem = group.getItem(p).clone();
                ItemMeta catMeta = catItem.getItemMeta();
                List<String> catLore = catMeta.getLore();

                String id = group.getKey().getNamespace() + ":" + group.getKey().getKey();
                if (catLore == null) {
                    catLore = new ArrayList<>(1);
                }
                catLore.set(catLore.size() - 1, ChatColors.color("&6ID: " + id)); // Replaces the "Click to Open" line
                catLore.add(ChatColors.color("&a点击复制到聊天栏"));
                catMeta.setLore(catLore);
                catItem.setItemMeta(catMeta);
                menu.replaceExistingItem(i, catItem);
                menu.addMenuClickHandler(i, (p1, s1, i1, a1) -> {
                    ClipboardUtil.send(p1, "&d点击复制: " + id, "&d点击复制", id);
                    return false;
                });
            }
        }

        if (page != 1) {
            menu.replaceExistingItem(46, Converter.getItem(Material.LIME_STAINED_GLASS_PANE, "&a上一页"));
            menu.addMenuClickHandler(46, (pl, s, is, action) -> {
                populateCategoryMenu(menu, groups, page - 1, p);
                return false;
            });
        }

        if (getItemGroupOrNull(groups, 45 * page) != null) {
            menu.replaceExistingItem(52, Converter.getItem(Material.LIME_STAINED_GLASS_PANE, "&a下一页"));
            menu.addMenuClickHandler(52, (pl, s, is, action) -> {
                populateCategoryMenu(menu, groups, page + 1, p);
                return false;
            });
        }

    }

    private static ItemGroup getItemGroupOrNull(List<ItemGroup> groups, int index) {
        return index < groups.size() ? groups.get(index) : null;
    }
}
