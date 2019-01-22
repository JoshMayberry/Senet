package com.example.android.senet;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int teamA_score = 0;
    int teamB_score = 0;
    boolean teamA_turn = true; //true: It is team A's turn; false: It is team A's turn

    //See: https://www.tutorialspoint.com/java/util/arrays_fill_boolean.htm
    boolean bones_state[] = new boolean[] {false, false, false, false}; //The state of each bone; 0 (face down) or 1 (face up)
    int bones_id[] = new int[] {R.id.bone1, R.id.bone2, R.id.bone3, R.id.bone4}; //The view id for each bone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetScore();
    }

    //Score Methods
    /** Updates the score for team A.
     *
     * Example Input: teamA_updateScore()
     */
    private void teamA_updateScore(){
        TextView scoreView = findViewById(R.id.teamA_score);
        scoreView.setText(String.valueOf(teamA_score));
    }
    /** Updates the score for team A.
     *
     * Example Input: teamA_updateScore()
     */
    private void teamB_updateScore(){
        TextView scoreView = findViewById(R.id.teamB_score);
        scoreView.setText(String.valueOf(teamB_score));
    }

    /** Increments the score for team A.
     *
     * Use For: A button's onClick attribute
     */
    public void onTeamA_addScore(View buttonView){
        teamA_score += 1;
        teamA_updateScore();
    }
    /** Decrements the score for team A.
     *
     * Use For: A button's onClick attribute
     */
    public void onTeamA_removeScore(View buttonView){
        if (teamA_score > 0) {
            teamA_score -= 1;
            teamA_updateScore();
        }
    }
    /** Increments the score for team B.
     *
     * Use For: A button's onClick attribute
     */
    public void onTeamB_addScore(View buttonView){
        teamB_score += 1;
        teamB_updateScore();
    }
    /** Decrements the score for team B.
     *
     * Use For: A button's onClick attribute
     */
    public void onTeamB_removeScore(View buttonView) {
        if (teamB_score > 0) {
            teamB_score -= 1;
            teamB_updateScore();
        }
    }

    /** A button event version of resetScore().
     *
     * Use For: A button's onClick attribute
     */
    public void onResetScore(View buttonView){
        resetScore();
    }
    /** Resets the score for both teams.
     *
     * Example Input: resetScore()
     */
    private void resetScore(){
        teamA_score = 0;
        teamB_score = 0;

        teamA_updateScore();
        teamB_updateScore();

        ImageView currentTeamView = findViewById(R.id.currentTeam);
        currentTeamView.setColorFilter(ContextCompat.getColor(this, R.color.colorTeamNull));
        currentTeamView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTeamNull_background));
    }

    //Bone Methods
    /** Creates new values for the given bone.
     *
     * @param boneIndex - The index number of the bone to roll
     *
     * Example Input: rollBone(0)
     */
    private void rollBone(int boneIndex) {
        // See: https://developer.android.com/reference/java/util/Random
        Random random = new Random();
        bones_state[boneIndex] = random.nextBoolean();
    }

    /** Updates the image for a bone.
     *
     * @param boneIndex - The index number of the bone to update
     *
     * Example Input: updateBone_picture(0)
     */
    private void updateBone_picture(int boneIndex) {
        ImageView view = findViewById(bones_id[boneIndex]);
        if (bones_state[boneIndex]) {
            //Special thanks to vincent091 for how to get a drawable object on: https://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22/29117227#29117227
            Drawable boneUp = ContextCompat.getDrawable(this, R.drawable.bone_up);
            view.setImageDrawable(boneUp);
        } else {
            Drawable boneDown = ContextCompat.getDrawable(this, R.drawable.bone_down);
            view.setImageDrawable(boneDown);
        }
    }

    /** Updates the GUI with what the bones have rolled.
     *
     * Use For: A button's onClick attribute
     */
    public void onDisplayBones(View buttonView) {
        //Show whose turn it is
        ImageView currentTeamView = findViewById(R.id.currentTeam);
        if (teamA_turn){
            teamA_turn = false;
            //Special thanks to Hardik for how to change the tint of an image on: https://stackoverflow.com/questions/20121938/how-to-set-tint-for-an-image-view-programmatically-in-android/20121975#20121975
            currentTeamView.setColorFilter(ContextCompat.getColor(this, R.color.colorTeamA));
            currentTeamView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTeamA_background));
        } else {
            teamA_turn = true;
            currentTeamView.setColorFilter(ContextCompat.getColor(this, R.color.colorTeamB));
            currentTeamView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTeamB_background));
        }

        //See: https://www.w3schools.com/java/java_for_loop.asp
        int total = 0;
        for (int boneIndex = 0; boneIndex < 4; boneIndex++){
            rollBone(boneIndex);
            updateBone_picture(boneIndex);
            //Count Bone
            if (bones_state[boneIndex]){
                total += 1;
            }
        }
        //If all bones are face down, that is a 5
        if (total == 0){
            total = 5;
        }
        TextView view_boneTotal = findViewById(R.id.boneTotal);
        view_boneTotal.setText(String.valueOf(total));
    }
}
