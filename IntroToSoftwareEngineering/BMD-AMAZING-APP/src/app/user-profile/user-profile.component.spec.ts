import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserProfileComponent } from './user-profile.component';
import { AuthenticationService } from '../authentication.service';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { User } from '../user';

describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;
  let mockAuthService: jasmine.SpyObj<AuthenticationService>;

  const mockUser: User = { id: 1, username: 'testuser' };

  beforeEach(async () => {
    // Create a mock AuthenticationService with a spy on getCurrentUser
    mockAuthService = jasmine.createSpyObj('AuthenticationService', ['getCurrentUser']);
    mockAuthService.getCurrentUser.and.returnValue(mockUser);

    await TestBed.configureTestingModule({
      declarations: [UserProfileComponent],
      imports: [RouterTestingModule],
      providers: [
        { provide: AuthenticationService, useValue: mockAuthService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UserProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should retrieve the current user on initialization', () => {
    component.ngOnInit();
    expect(component.currentUser).toEqual(mockUser);
  });

  it('should show modal when showModal is called', () => {
    component.showModal();
    expect(component.isModalVisible).toBeTrue();
  });

  it('should hide modal when closeModal is called', () => {
    component.showModal();
    component.closeModal();
    expect(component.isModalVisible).toBeFalse();
  });
});
