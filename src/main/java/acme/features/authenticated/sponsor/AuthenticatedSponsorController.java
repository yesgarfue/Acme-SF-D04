
package acme.features.authenticated.sponsor;

import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.roles.Sponsor;

@Controller
public class AuthenticatedSponsorController extends AbstractController<Authenticated, Sponsor> {
}
