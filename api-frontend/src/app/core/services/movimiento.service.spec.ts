import { TestBed } from '@angular/core/testing';
import {provideHttpClient} from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

import { MovimientoService } from './movimiento.service';

describe('MovimientoService', () => {
  let service: MovimientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        MovimientoService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(MovimientoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
