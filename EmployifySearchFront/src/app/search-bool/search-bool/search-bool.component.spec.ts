import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchBoolComponent } from './search-bool.component';

describe('SearchBoolComponent', () => {
  let component: SearchBoolComponent;
  let fixture: ComponentFixture<SearchBoolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchBoolComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchBoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
