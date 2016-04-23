/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.javaclasses;

import mygame.appstates.util.ScenarioAppState;

/**
 *
 * @author GAMEOVER
 */
public final class Constants {

    public static final class UserData {

        public static final String SPEED = "speed";
        public static final String CHASE = "chase";
        public static final String MEASURES = "measures";
        public static final String PLAYER_NODE = "playerNode";
        public static final String PLAYER = "player";
        public static final String WALK_DIRECTION = "walkDirection";
        public static final String CAMERA_NODE = "cameraNode";
        public static final String ENEMY_NODE = "enemyNode";
        public static final String FRANKESTEIN = "frankestein";
        public static final String DOOR_TYPE = "doorType";
        public static final String DIRECTION = "direction";
        public static final String LEFT_WALL = "leftWall";
        public static final String RIGHT_WALL = "rightWall";
        public static final String TOP_WALL = "topWall";
        public static final String BOTTOM_WALL = "bottomWall";
        public static final String DOORS_NODE = "doorsNode";
        public static final String PLAYER_ACTIONS = "playerActions";
        public static final String PLAYER_OPTIONS = "playerOptions";
        public static final String ROOM_APP = "roomApp";
        public static final String CORRESPONDENT_DOOR = "correspondentDoor";
        public static final String PLAYER_USING_DOOR = "playerUsingDoor";
        public static final String DOOR_ORIENTATION = "doorOrientation";
        public static final String NAME = "name";
        public static final String SYMETRIC_DOOR_NAME = "symetricDoorName";
        public static final String PLAYER_CONTROL = "playerControl";
    }

    public static final class Mapping {

        public static final String UP = "up";
        public static final String DOWN = "down";
        public static final String LEFT = "left";
        public static final String RIGHT = "right";
        public static final String RETURN = "return";
    }

    public static final class Doors {

        public static final String COUNTRYARD_TO_ENTRANCE = "countryardToEntrance";
        public static final String ENTRANCE_TO_COUNTRYARD = "entranceToCountryard";
        public static final String ENTRANCE_TO_CORRIDOR = "entranceToCorridor";
        public static final String CORRIDOR_TO_ENTRANCE = "corridorToEntrance";
        public static final String CORRIDOR_TO_OFFICE = "corridorToOffice";
        public static final String OFFICE_TO_CORRIDOR = "officeToCorridor";
        public static final String CORRIDOR_TO_CAGES = "corridorToCages";
        public static final String CAGES_TO_CORRIDOR = "cagesToCorridor";
        public static final String CORRIDOR_TO_MAINTENANCE = "corridorToMaintenance";
        public static final String MAINTENANCE_TO_CORRIDOR = "maintenanceToCorridor";
        public static final String CORRIDOR_TO_POWER = "corridorToGenerator";
        public static final String POWER_TO_CORRIDOR = "generatorToCorridor";
    }

    public static final class ObserverPattern {

        public static final String NEXT_DOOR = "nextDoor";
        public static final String NOT_NEXT_DOOR = "!nextDoor";
        public static final String ENTERED_DOOR = "enteredDoor";
    }
   
}
