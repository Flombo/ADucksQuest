package GameLogic.Movement.MovementHelper;

import GameObjects.GameObjectEnums.Frames.PlayerWalkFrames;
import GameObjects.Player.Player;

public class playerFrameChecker {

	public void checkWalkFrameRight(Player player){
		if(!player.getWalkFrame().equals(PlayerWalkFrames.Player_Default_Right)
				&& !player.getWalkFrame().equals(PlayerWalkFrames.Player_Right1)
				&&!player.getWalkFrame().equals(PlayerWalkFrames.Player_Right2)){
			player.setWalkFrame(PlayerWalkFrames.Player_Default_Right);
		}
	}

	public void checkWalkFrameUp(Player player){
		if(!player.getWalkFrame().equals(PlayerWalkFrames.Player_Default_Up)
				&& !player.getWalkFrame().equals(PlayerWalkFrames.Player_Up1)
				&&!player.getWalkFrame().equals(PlayerWalkFrames.Player_Up2)){
			player.setWalkFrame(PlayerWalkFrames.Player_Default_Up);
		}
	}

	public void checkWalkFrameDown(Player player){
		if(!player.getWalkFrame().equals(PlayerWalkFrames.Player_Default_Down)
				&& !player.getWalkFrame().equals(PlayerWalkFrames.Player_Down1)
				&& !player.getWalkFrame().equals(PlayerWalkFrames.Player_Down2)){
			player.setWalkFrame(PlayerWalkFrames.Player_Default_Down);
		}
	}

	public void checkWalkFrameLeft(Player player){
		if(!player.getWalkFrame().equals(PlayerWalkFrames.Player_Default_Left)
				&& !player.getWalkFrame().equals(PlayerWalkFrames.Player_Left1)
				&& !player.getWalkFrame().equals(PlayerWalkFrames.Player_Left2)){
			player.setWalkFrame(PlayerWalkFrames.Player_Default_Left);
		}
	}
}
