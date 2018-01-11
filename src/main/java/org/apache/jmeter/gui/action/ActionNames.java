/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.jmeter.gui.action;

/**
 * Collect all the action names together in one place.
 * This helps to ensure that there are no duplicates
 * 
 */
public final class ActionNames {

    public static final String ABOUT            = "about";
    public static final String ACTION_SHUTDOWN  = "shutdown";
    public static final String ACTION_START     = "start";
    public static final String ACTION_START_NO_TIMERS = "start_no_timers";
    public static final String ACTION_STOP      = "stop";
    public static final String ADD              = "Add";
    public static final String ADD_ALL          = "add_all";
    public static final String ADD_PARENT       = "Add Parent";
    public static final String ANALYZE_FILE     = "Analyze File";
    public static final String CHANGE_LANGUAGE  = "change_language";
    public static final String CHANGE_PARENT    = "Change Parent";
    public static final String CHECK_DIRTY      = "check_dirty";
    public static final String CHECK_REMOVE     = "check_remove";
    public static final String CLEAR            = "action.clear";
    public static final String CLEAR_ALL        = "action.clear_all";
    public static final String CLOSE            = "close";
    public static final String COLLAPSE_ALL     = "collapse all";
    public static final String COPY             = "Copy";
    public static final String CUT              = "Cut";
    public static final String DEBUG_ON         = "debug_on";
    public static final String DEBUG_OFF        = "debug_off";
    public static final String DISABLE          = "disable";
    /** Copy, then paste afterwards */
    public static final String DUPLICATE        = "duplicate";
    public static final String EDIT             = "edit";
    public static final String ENABLE           = "enable";
    public static final String EXIT             = "exit";
    public static final String EXPAND_ALL       = "expand all";
    public static final String FUNCTIONS        = "functions";
    public static final String HELP             = "help";
    public static final String HEAP_DUMP        = "heap_dump";
    public static final String LAF_PREFIX       = "laf:"; // Look and Feel prefix
    public static final String LOGGER_PANEL_ENABLE_DISABLE     = "logger_panel_enable_disable";
    public static final String MERGE            = "merge";
    public static final String OPEN             = "open";
    public static final String OPEN_RECENT      = "open_recent";
    public static final String TEMPLATES        = "templates";
    public static final String PASTE            = "Paste";
    public static final String REMOTE_EXIT      = "remote_exit";
    public static final String REMOTE_EXIT_ALL  = "remote_exit_all";
    public static final String REMOTE_SHUT      = "remote_shut";
    public static final String REMOTE_SHUT_ALL  = "remote_shut_all";
    public static final String REMOTE_START     = "remote_start";
    public static final String REMOTE_START_ALL = "remote_start_all";
    public static final String REMOTE_STOP      = "remote_stop";
    public static final String REMOTE_STOP_ALL  = "remote_stop_all";
    public static final String REMOVE           = "remove";
    public static final String RESET_GUI        = "reset_gui";
    public static final String REVERT_PROJECT   = "revert_project";
    public static final String SAVE             = "save";
    public static final String SAVE_ALL_AS      = "save_all_as";
    public static final String SAVE_AS          = "save_as";
    public static final String SAVE_AS_TEST_FRAGMENT          = "save_as_test_fragment";
    public static final String SAVE_GRAPHICS    = "save_graphics";
    public static final String SAVE_GRAPHICS_ALL= "save_graphics_all";
    public static final String SSL_MANAGER      = "sslManager";
    public static final String STOP_THREAD      = "stop_thread";
    public static final String SUB_TREE_LOADED  = "sub_tree_loaded";
    public static final String SUB_TREE_MERGED  = "sub_tree_merged";
    public static final String SUB_TREE_SAVED   = "sub_tree_saved";
    public static final String TOGGLE           = "toggle";  //enable/disable
    public static final String TOOLBAR          = "toolbar";
    public static final String WHAT_CLASS       = "what_class";
    public static final String SEARCH_TREE      = "search_tree";
    public static final String SEARCH_RESET     = "search_reset";
    public static final String MOVE_UP          = "move_up";
    public static final String MOVE_DOWN        = "move_down";
    public static final String MOVE_LEFT        = "move_left";
    public static final String MOVE_RIGHT       = "move_right";
    public static final String UNDO             = "undo";
    public static final String REDO             = "redo";
    public static final String QUICK_COMPONENT  = "quick_component";
    public static final String COLLAPSE         = "collapse";
    public static final String EXPAND           = "expand";
    public static final String RUN_TG           = "run_tg";
    public static final String RUN_TG_NO_TIMERS = "run_tg_no_timers";
    public static final String VALIDATE_TG      = "validate_tg"; //$NON-NLS-1$

    // Prevent instantiation
    private ActionNames() {}
}
