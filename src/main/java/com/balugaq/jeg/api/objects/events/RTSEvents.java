package com.balugaq.jeg.api.objects.events;

import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import lombok.Getter;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author balugaq
 * @since 1.3
 */
public class RTSEvents {
    /**
     * Represents the event when the RTS is closed.
     */
    @Getter
    public static class CloseRTSEvent extends Event {
        private static final HandlerList HANDLERS = new HandlerList();
        private final Player player;
        private final AnvilGUI.StateSnapshot stateSnapshot;
        private final SlimefunGuideMode guideMode;

        /**
         * Constructs a new CloseRTSEvent.
         *
         * @param player        The player who closed the RTS.
         * @param stateSnapshot The state snapshot of the anvil GUI.
         * @param guideMode     The guide mode.
         */
        public CloseRTSEvent(Player player, AnvilGUI.StateSnapshot stateSnapshot, SlimefunGuideMode guideMode) {
            super(!Bukkit.isPrimaryThread());
            this.player = player;
            this.stateSnapshot = stateSnapshot;
            this.guideMode = guideMode;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        public static @NotNull HandlerList getHandlerList() {
            return HANDLERS;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        @NotNull
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }
    }

    /**
     * Represents the event when the RTS is opened.
     */
    @Getter
    public static class OpenRTSEvent extends Event {
        private static final HandlerList HANDLERS = new HandlerList();
        private final Player player;
        private final AnvilInventory openingInventory;
        private final SlimefunGuideMode guideMode;
        private @Nullable
        final String presetSearchTerm;

        /**
         * Constructs a new OpenRTSEvent.
         *
         * @param player           The player who opened the RTS.
         * @param openingInventory The opening inventory.
         * @param guideMode        The guide mode.
         */
        public OpenRTSEvent(Player player, AnvilInventory openingInventory, SlimefunGuideMode guideMode) {
            this(player, openingInventory, guideMode, null);
        }

        /**
         * Constructs a new OpenRTSEvent.
         *
         * @param player           The player who opened the RTS.
         * @param openingInventory The opening inventory.
         * @param guideMode        The guide mode.
         * @param presetSearchTerm The preset search term.
         */
        public OpenRTSEvent(Player player, AnvilInventory openingInventory, SlimefunGuideMode guideMode, String presetSearchTerm) {
            super(!Bukkit.isPrimaryThread());
            this.player = player;
            this.openingInventory = openingInventory;
            this.guideMode = guideMode;
            this.presetSearchTerm = presetSearchTerm;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        public static @NotNull HandlerList getHandlerList() {
            return HANDLERS;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        @NotNull
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }
    }

    /**
     * Represents the event when the search term changes in the RTS.
     */
    @Getter
    public static class SearchTermChangeEvent extends Event {
        private static final HandlerList HANDLERS = new HandlerList();
        private final Player player;
        private final InventoryView inventoryView;
        private final AnvilInventory openingInventory;
        private final String oldSearchTerm;
        private final String newSearchTerm;
        private final SlimefunGuideMode guideMode;

        /**
         * Constructs a new SearchTermChangeEvent.
         *
         * @param player           The player who changed the search term.
         * @param inventoryView    The inventory view.
         * @param openingInventory The opening inventory.
         * @param oldSearchTerm    The old search term.
         * @param newSearchTerm    The new search term.
         * @param guideMode        The guide mode.
         */
        public SearchTermChangeEvent(Player player, InventoryView inventoryView, AnvilInventory openingInventory, String oldSearchTerm, String newSearchTerm, SlimefunGuideMode guideMode) {
            super(false);
            this.player = player;
            this.inventoryView = inventoryView;
            this.openingInventory = openingInventory;
            this.oldSearchTerm = oldSearchTerm;
            this.newSearchTerm = newSearchTerm;
            this.guideMode = guideMode;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        public static @NotNull HandlerList getHandlerList() {
            return HANDLERS;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        @NotNull
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }
    }

    /**
     * Represents the event when an item in the anvil GUI is clicked.
     */
    @Getter
    public static class ClickAnvilItemEvent extends Event implements Cancellable {
        private static final HandlerList HANDLERS = new HandlerList();
        private final Player player;
        private final AnvilGUI.StateSnapshot stateSnapshot;
        private final SlimefunGuideMode guideMode;
        private final int slot;
        private boolean cancelled;

        /**
         * Constructs a new ClickAnvilItemEvent.
         *
         * @param player        The player who clicked the anvil item.
         * @param stateSnapshot The state snapshot of the anvil GUI.
         * @param slot          The slot that was clicked.
         * @param guideMode     The guide mode.
         */
        public ClickAnvilItemEvent(Player player, AnvilGUI.StateSnapshot stateSnapshot, int slot, SlimefunGuideMode guideMode) {
            super(!Bukkit.isPrimaryThread());
            this.player = player;
            this.stateSnapshot = stateSnapshot;
            this.slot = slot;
            this.guideMode = guideMode;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        public static @NotNull HandlerList getHandlerList() {
            return HANDLERS;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        @NotNull
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }

        /**
         * Checks if the event is cancelled.
         *
         * @return true if the event is cancelled, false otherwise
         */
        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        /**
         * Sets the cancellation state of the event.
         *
         * @param cancelled the cancellation state
         */
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }

    /**
     * Represents the event when the page changes in the RTS.
     */
    @Getter
    public static class PageChangeEvent extends Event implements Cancellable {
        private static final HandlerList HANDLERS = new HandlerList();
        private final Player player;
        private final AnvilInventory openingInventory;
        private final SlimefunGuideMode guideMode;
        private final int oldPage;
        private final int newPage;
        private boolean cancelled;

        /**
         * Constructs a new PageChangeEvent.
         *
         * @param player           The player who changed the page.
         * @param openingInventory The opening inventory.
         * @param oldPage          The old page number.
         * @param newPage          The new page number.
         * @param guideMode        The guide mode.
         */
        public PageChangeEvent(Player player, AnvilInventory openingInventory, int oldPage, int newPage, SlimefunGuideMode guideMode) {
            super(!Bukkit.isPrimaryThread());
            this.player = player;
            this.openingInventory = openingInventory;
            this.oldPage = oldPage;
            this.newPage = newPage;
            this.guideMode = guideMode;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        public static @NotNull HandlerList getHandlerList() {
            return HANDLERS;
        }

        /**
         * Returns the handler list.
         *
         * @return the handler list
         */
        @NotNull
        @Override
        public HandlerList getHandlers() {
            return HANDLERS;
        }

        /**
         * Sets the cancellation state of the event.
         *
         * @param cancelled the cancellation state
         */
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
}