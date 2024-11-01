package com.example.samplevideo.utils;

public class GFNode<E> {
	E item;
	GFNode<E> next;
	GFNode<E> prev;

	GFNode(GFNode<E> prev, E element, GFNode<E> next) {
		this.item = element;
		this.next = next;
		this.prev = prev;
	}
}
