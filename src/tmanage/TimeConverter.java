/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

/**
 *
 * @author martin
 */
public class TimeConverter {
    //converting seconds to normal HH:MM:SS time string
    public static String getFormattedTime(long secs) {
        int mins = (int) secs / 60;
        int remainderSecs = (int) secs - (mins * 60);
        int hours = (int) mins / 60;
        int remainderMins = mins - (hours * 60);

        return (hours < 10 ? "0" : "") + hours + ":"
                + (remainderMins < 10 ? "0" : "") + remainderMins + ":"
                + (remainderSecs < 10 ? "0" : "") + remainderSecs;
    }
}
