package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Iterators {
	public static <E> Iterator<E> iteratorWithPredicate(
			final Iterator<? extends E> iterator, final Predicate<? super E> p) {
		return new Iterator<E>() {

			private E next = null;

			public boolean hasNext() {
				while (next == null) {
					if (!iterator.hasNext()) {
						return false;
					}
					next = iterator.next();
					if (!p.predicate(next)) {
						next = null;
					}
				}
				return true;
			}

			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				E n = next;
				next = null;
				return n;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Create an iterator on the array, from element with index
	 * <tt>fromIndex</tt> inclusive to element with index <tt>toIndex</tt>
	 * exclusive.
	 */
	public static <E> Iterator<E> subArrayIterator(final E[] array,
			final int fromIndex, final int toIndex) {
		return Arrays.asList(array).subList(fromIndex, toIndex).iterator();
	}

	public static <E> Iterator<E> appendElement(final E element,
			final Iterator<? extends E> it) {
		return new Iterator<E>() {
			private boolean first = true;

			public boolean hasNext() {
				return first || it.hasNext();
			}

			public E next() {
				if (first) {
					first = false;
					return element;
				} else {
					return it.next();
				}
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <E> Iterator<E> appendElement(final Iterator<? extends E> it,
			final E element) {
		return new Iterator<E>() {
			private boolean elementUsed = false;

			public boolean hasNext() {
				return it.hasNext() || !elementUsed;
			}

			public E next() {
				if (it.hasNext()) {
					return it.next();
				} else {
					elementUsed = true;
					return element;
				}
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <E> Iterator<E> append(final Iterator<? extends E> it1,
			final Iterator<? extends E> it2) {
		return new Iterator<E>() {
			private boolean first = true;

			public boolean hasNext() {
				return it1.hasNext() || it2.hasNext();
			}

			public E next() {
				if (it1.hasNext()) {
					return it1.next();
				} else {
					first = false;
					return it2.next();
				}
			}

			public void remove() {
				if (first) {
					it1.remove();
				} else {
					it2.remove();
				}
			}
		};
	}

	public static <E> Iterator<E> singleton(final E element) {
		return new Iterator<E>() {
			boolean used = false;

			@Override
			public boolean hasNext() {
				return !used;
			}

			@Override
			public E next() {
				if (!used) {
					used = true;
					return element;
				} else
					throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <E> List<E> fillList(List<E> list, Iterator<? extends E> it) {
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}

	/**
	 * Used mainly for testing. Test if the iterator <tt>it</tt> returns exactly
	 * the values contained in <tt>elements</tt>.
	 * 
	 * @param <E>
	 *            type of the elements.
	 * @param it
	 *            an iterator
	 * @param elements
	 *            an array of elements
	 * @return
	 */
	public static <E> boolean hasElements(Iterator<E> it, E... elements) {
		return Arrays.asList(elements).equals(fillList(new ArrayList<E>(), it));
	}

	/**
	 * Used mainly for testing. Returns the number of elements returned by
	 * <tt>it</tt>.
	 * 
	 * @param <E>
	 *            type of the elements.
	 * @param it
	 *            an iterator
	 * 
	 * @return the number of elements returned by the iterator
	 */
	public static <E> int size(Iterator<E> it) {
		int size = 0;
		while (it.hasNext()) {
			it.next();
			size++;
		}
		return size;
	}

	public static <E> Iterator<E> emptyIterator() {
		return new Iterator<E>() {
			public boolean hasNext() {
				return false;
			}

			public E next() {
				throw new NoSuchElementException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static void print(Iterator<?> it, String separator) {
		while (it.hasNext()) {
			System.out.print(it.next() + separator);
		}
		System.out.println();
	}
}
