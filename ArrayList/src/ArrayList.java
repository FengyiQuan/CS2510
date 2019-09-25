import java.util.ArrayList;

class Utils {

  // Effect: swaps the items in the given list at the given indexes
  <T> void swap(ArrayList<T> alist, int index1, int index2) {
    alist.set(index2, alist.set(index1, alist.get(index2)));
  }

//returns the index of the first item passing the predicate, or -1 if no such
  // item was found
  <T> int find(ArrayList<T> arr, IPred<T> whichOne) {
    return this.findHelp(arr, whichOne, 0);
  }

  // returns the index of the first item passing the predicate at or after the
  // given index, or -1 if no such item was found

  <T> int findHelp(ArrayList<T> arr, IPred<T> whichOne, int index) {
    if (index == arr.size()) {
      return -1;
    }
    if (whichOne.apply(arr.get(index))) {
      return index;
    }
    else {
      return findHelp(arr, whichOne, index + 1);
    }
  }

  // find the target in the sorted list
  int binarySearch(ArrayList<String> strings, String target) {
    return this.binarySearchHelp(strings, target, 0, strings.size() - 1);
  }

  // find the target in the sorted list using binary search
  int binarySearchHelp(ArrayList<String> strings, String target, int low, int high) {
    if (low > high) {
      return -1;
    }

    int mid = (low + high) / 2;
    String atMid = strings.get(mid);

    if (atMid.compareTo(target) == 0) {
      return mid;
    }
    else if (atMid.compareTo(target) > 0) {
      return this.binarySearchHelp(strings, target, low, mid - 1);
    }
    else {
      return this.binarySearchHelp(strings, target, mid + 1, high);
    }
  }
}
