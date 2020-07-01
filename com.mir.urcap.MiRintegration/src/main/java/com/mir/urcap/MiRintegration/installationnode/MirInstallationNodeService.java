package com.mir.urcap.MiRintegration.installationnode;

import java.util.Locale;

import com.mir.urcap.MiRintegration.common.MirDaemonService;
import com.mir.urcap.MiRintegration.style.Style;
import com.mir.urcap.MiRintegration.style.V3Style;
import com.mir.urcap.MiRintegration.style.V5Style;
import com.mir.urcap.MiRintegration.urcontrol.URControlConfig;
import com.ur.urcap.api.contribution.InstallationNodeService;
import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.SystemAPI;
import com.ur.urcap.api.domain.data.DataModel;

public class MirInstallationNodeService implements SwingInstallationNodeService<MirInstallationNodeContribution, MirInstallationNodeView> {

	private MirDaemonService daemonService;
	
	public MirInstallationNodeService(MirDaemonService daemonService) {
		this.daemonService = daemonService;
		URControlConfig.setPowerSupplyLimit(480);
	}
	
	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle(Locale locale) {
		return "MiR Integration";
	}

	@Override
	public MirInstallationNodeView createView(ViewAPIProvider apiProvider) {
		SystemAPI systemAPI = apiProvider.getSystemAPI();
		Style style = systemAPI.getSoftwareVersion().getMajorVersion() >= 5 ? new V5Style() : new V3Style();
		
		return new MirInstallationNodeView(style);
	}

	@Override
	public MirInstallationNodeContribution createInstallationNode(InstallationAPIProvider apiProvider,
			MirInstallationNodeView view, DataModel model, CreationContext context) {
		return new MirInstallationNodeContribution(apiProvider, view, model, daemonService);
	}

}

