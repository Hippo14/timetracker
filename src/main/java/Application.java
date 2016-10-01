import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import dao.ApplicationDao;

/**
 * Created by MSI on 2016-10-01.
 */
public class Application {

    private static final int MAX_TITLE_LENGTH = 1024;
    private final DatabaseConfig databaseConfig = DatabaseConfig.getInstance();

    public void init() {
        String lastTitle = "none";
        String lastProcess = "none";
        long lastChange = System.currentTimeMillis();

        while (true)
        {
            String currentTitle = getActiveWindowTitle();
            String currentProcess = getActiveWindowProcess();
            if (!lastTitle.equals(currentTitle))
            {
                long change = System.currentTimeMillis();
                double time = ((change - lastChange) / 1000.0);
                lastChange = change;
                System.out.println("Change! Last title: " + lastTitle + " lastProcess: " + lastProcess + " time: " + time + " seconds");
                addToDB(lastTitle, lastProcess, time);
                lastTitle = currentTitle;
                lastProcess = currentProcess;
            }
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException ex)
            {
                // ignore
            }
        }
    }

    private static void addToDB(String lastTitle, String lastProcess, double time) {
        ApplicationDao.insert(DatabaseConfig.getConnection(), lastTitle, lastProcess, time);
    }

    private static String getActiveWindowTitle()
    {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        WinDef.HWND foregroundWindow = User32DLL.GetForegroundWindow();
        User32DLL.GetWindowTextW(foregroundWindow, buffer, MAX_TITLE_LENGTH);
        String title = Native.toString(buffer);
        return title;
    }

    private static String getActiveWindowProcess()
    {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        PointerByReference pointer = new PointerByReference();
        WinDef.HWND foregroundWindow = User32DLL.GetForegroundWindow();
        User32DLL.GetWindowThreadProcessId(foregroundWindow, pointer);
        Pointer process = Kernel32.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, pointer.getValue());
        Psapi.GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
        String processName = Native.toString(buffer);
        return processName;
    }

    static class Psapi
    {
        static
        {
            Native.register("psapi");
        }

        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    static class Kernel32
    {
        static
        {
            Native.register("kernel32");
        }

        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;

        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    static class User32DLL
    {
        static
        {
            Native.register("user32");
        }

        public static native int GetWindowThreadProcessId(WinDef.HWND hWnd, PointerByReference pref);
        public static native WinDef.HWND GetForegroundWindow();
        public static native int GetWindowTextW(WinDef.HWND hWnd, char[] lpString, int nMaxCount);
    }

}
