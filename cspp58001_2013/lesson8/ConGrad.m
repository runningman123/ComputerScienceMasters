function [ x ] = ConGrad( A, b )
%
n = numel(b);

tol = 1.e-6;
err = 1000;

x0 = zeros(n,1);
r0 = b;
p0 = r0;
iter = 0;
while (err > tol)
    iter = iter + 1
    a = r0'*r0/(p0'*A*p0);
    x1  = x0 + a*p0;
    r1 = r0 - a*A*p0;
    beta = (r1'*r1)/(r0'*r0);
    p1 = r1 + beta*p0;
    err = norm(r1)
    p0 = p1; x0 = x1; r0 = r1;
end

x=x0;

end