package cz.msvab.gradle.sftp

import org.apache.camel.ConsumerTemplate
import org.apache.camel.Exchange
import org.apache.camel.ProducerTemplate
import org.apache.camel.component.file.remote.SftpComponent
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.DefaultConsumerTemplate
import org.apache.camel.impl.DefaultProducerTemplate
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.util.AvailablePortFinder
import org.junit.Test

class SftpPluginTest {

    private DefaultCamelContext camelContext = camelContext()
    private availablePort = AvailablePortFinder.createPrivate().nextAvailable

    @Test
    void shouldMakeStartSftpTaskAvailable() {
        def project = ProjectBuilder.builder().build()
        project.apply plugin: 'sftp'

        assert project.tasks.startSftp instanceof StartSftpTask
    }

    @Test
    void shouldBeAbleToUploadAndDownloadAFile() {
        def project = ProjectBuilder.builder().build()
        project.apply plugin: 'sftp'
        project.sftp.port = availablePort
        project.sftp.users = ['foo': 'bar']
        project.tasks.startSftp.execute()

        producerTemplate().sendBodyAndHeader("sftp://foo:bar@localhost:$availablePort?disconnect=true", 'Hello!', Exchange.FILE_NAME, 'test')

        def testFile = consumerTemplate().receiveBody("sftp://foo:bar@localhost:$availablePort/?disconnect=true&fileName=test", String.class)
        assert testFile == 'Hello!'
    }

    ConsumerTemplate consumerTemplate() {
        def template = new DefaultConsumerTemplate(camelContext)
        template.start()
        return template
    }

    ProducerTemplate producerTemplate() {
        def template = new DefaultProducerTemplate(camelContext)
        template.start()
        return template
    }

    static DefaultCamelContext camelContext() {
        def context = new DefaultCamelContext()
        context.addComponent('sftp', new SftpComponent())
        return context
    }
}
