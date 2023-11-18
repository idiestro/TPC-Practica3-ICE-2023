package server1;

import Ice.Current;
import Ice.IntHolder;

public class CalculadoraI extends Demo._CalculadoraDisp {

	@Override
	public int sumar(int a, int b, Current __current) {
		// TODO Auto-generated method stub
		return a+b;
	}

	@Override
	public int restar(int a, int b, Current __current) {
		// TODO Auto-generated method stub
		return a-b;
	}

	@Override
	public int producto(int a, int b, Current __current) {
		// TODO Auto-generated method stub
		return a*b;
	}

	@Override
	public void cociente(int a, int b, IntHolder coc, IntHolder res,
			Current __current) {
		// TODO Auto-generated method stub
		coc.value = a / b;
		res.value = a % b;
	}
	
}
