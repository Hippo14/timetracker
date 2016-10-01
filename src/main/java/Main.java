import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.Scanner;

/**
 * Created by MSI on 2016-10-01.
 */
public class Main {

    private static final int MAX_TITLE_LENGTH = 1024;
    private static boolean running = true;


    public static void main(String[] args) throws Exception {
        while (running) {
            Signal.handle(new Signal("INT"), new SignalHandler() {
                // Signal handler method
                public void handle(Signal signal) {
                    running = false;
                }
            });

            Thread.sleep(1000);

            getActiveWindow();
        }
    }

    public static void getActiveWindow() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        System.out.println("Active window title: " + Native.toString(buffer));
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        System.out.println("rect = " + rect);
    }

}
