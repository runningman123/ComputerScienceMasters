#include <math.h>

void resid(double **res, double **u, double **rhs, int n, double c, double dt, double **s)
/*
Returns minus the residual for the model problem. Input quantities are u[1..n][1..n] and
rhs[1..n][1..n], while res[1..n][1..n] is returned.
*/
{
  int i,j;
  double h,h2i;
  h=1.0/(n-1);
  h2i=1.0/(h*h);
  c=c*h2i;
  double totalResid=0;
  /* Interior points.*/
  for (j=2;j<n;j++) 
    for (i=2;i<n;i++)
      res[i][j] = c*(u[i+1][j]+u[i-1][j]+u[i][j+1]+u[i][j-1]) -
			(1+4.0*c)*u[i][j] + rhs[i][j]+dt*s[i][j];
      totalResid+=res[i][j]*res[i][j];
  /* Boundary points.*/
  for (i=1;i<=n;i++) 
    res[i][1]=res[i][n]=res[1][i]=res[n][i]=0.0;

  totalResid=sqrt(totalResid);
  //printf(" Residual: %f \n",totalResid);
}
