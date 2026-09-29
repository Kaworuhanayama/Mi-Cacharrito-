import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const sesionGuard: CanActivateFn = () => {
  const router = inject(Router);
  const haySesion = !!sessionStorage.getItem('mi-cacharrito-sesion');
  return haySesion ? true : router.createUrlTree(['/login']);
};