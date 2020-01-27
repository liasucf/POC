import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdmin, Admin } from 'app/shared/model/admin.model';
import { AdminService } from './admin.service';
import { AdminComponent } from './admin.component';
import { AdminDetailComponent } from './admin-detail.component';
import { AdminUpdateComponent } from './admin-update.component';

@Injectable({ providedIn: 'root' })
export class AdminResolve implements Resolve<IAdmin> {
  constructor(private service: AdminService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdmin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((admin: HttpResponse<Admin>) => {
          if (admin.body) {
            return of(admin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Admin());
  }
}

export const adminRoute: Routes = [
  {
    path: '',
    component: AdminComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Admins'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AdminDetailComponent,
    resolve: {
      admin: AdminResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Admins'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AdminUpdateComponent,
    resolve: {
      admin: AdminResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Admins'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AdminUpdateComponent,
    resolve: {
      admin: AdminResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Admins'
    },
    canActivate: [UserRouteAccessService]
  }
];
