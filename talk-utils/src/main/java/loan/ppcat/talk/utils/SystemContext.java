package loan.ppcat.talk.utils;

public class SystemContext {

    private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
    private static ThreadLocal<Integer> pageOffset = new ThreadLocal<Integer>();

    public static Integer getPageSize() {

        return pageSize.get();
    }

    public static void setPageSize(Integer _pageSize) {

        pageSize.set(_pageSize);
    }

    public static Integer getPageOffset() {

        return pageOffset.get();
    }

    public static void setPageOffset(Integer _pageOffset) {

        pageOffset.set(_pageOffset);
    }


    public static void clearPageOffset() {

        pageOffset.remove();
    }

    public static void clearPageSize() {

        pageSize.remove();
    }

    public static void clearAllData() {
        pageSize.remove();
        pageOffset.remove();
    }

}
